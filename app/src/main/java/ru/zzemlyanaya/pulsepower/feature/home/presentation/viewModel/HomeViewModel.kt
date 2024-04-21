package ru.zzemlyanaya.pulsepower.feature.home.presentation.viewModel

import androidx.compose.ui.text.AnnotatedString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.navigation.*
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.core.utils.ResourceProvider
import ru.zzemlyanaya.pulsepower.core.viewModel.BaseViewModel
import ru.zzemlyanaya.pulsepower.feature.history.domain.model.*
import ru.zzemlyanaya.pulsepower.feature.history.presentation.mapping.MembershipUiModelMapper
import ru.zzemlyanaya.pulsepower.feature.home.domain.interactor.StoreInteractor
import ru.zzemlyanaya.pulsepower.feature.home.presentation.model.PaymentState
import ru.zzemlyanaya.pulsepower.feature.home.presentation.model.contract.HomeContract
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.mapping.PlacesUiMapper
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.model.CityItemUiModel
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.viewModel.PlaceSelectViewModel
import ru.zzemlyanaya.pulsepower.feature.profile.domain.model.UserEntityProvider
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userEntityProvider: UserEntityProvider,
    private val interactor: StoreInteractor,
    private val mapper: MembershipUiModelMapper,
    private val placesMapper: PlacesUiMapper,
    private val resourceProvider: ResourceProvider,
    private val router: NavigationRouter
) : BaseViewModel<HomeContract.UiState, HomeContract.Intent>(router) {

    var timeOfTheDay = TimeOfTheDay.AllDay
    var type = MembershipType.Single
    var duration = MembershipDuration.MONTHS_3

    override fun getInitialState() = HomeContract.UiState()

    init {
        updateDataState { it.copy(
            userName = userEntityProvider.userEntity.name,
            favouritePlaces = userEntityProvider.userEntity.favouritePlacesText,
            timeOfTheDayOption = interactor.getTimeOptions(),
            typeOptions = interactor.getTypeOptions(),
            durationOptions = interactor.getDurationOptions()
        ) }

        updatePrice()
        loadActiveMemberships()
        listenSelectPlacesResult()
    }

    override fun handleIntent(intent: BaseIntent) {
        when (intent) {
            is HomeContract.Intent.SelectTimeOfTheDay -> onTimeOfTheDaySelect(intent.timeOfTheDay)
            is HomeContract.Intent.SelectType -> onTypeSelect(intent.membershipType)
            is HomeContract.Intent.SelectDuration -> onDurationSelect(intent.membershipDuration)
            is HomeContract.Intent.OpenProfile -> router.navigateTo(HomeDirections.profile)
            is HomeContract.Intent.OpenHistory -> router.navigateTo(HomeDirections.history)
            is HomeContract.Intent.SelectPlace -> router.navigateTo(MainDirections.placeSelect)
            is HomeContract.Intent.Pay -> startPayment()
            is HomeContract.Intent.ClosePaymentDialog -> updateDataState { it.copy(paymentState = PaymentState.NONE) }
            else -> super.handleIntent(intent)
        }
    }

    private fun loadActiveMemberships() {
        ioScope.launch {
            val membership = interactor.getActiveMemberships()
            if (membership == null) {
                updateDataState { it.copy(currentMembership = getNoMembershipString()) }
            } else {
                updateDataState { it.copy(currentMembership = mapper.mapMembership(membership).description) }
            }
        }
    }

    private fun onTimeOfTheDaySelect(timeOfTheDay: TimeOfTheDay) {
        this.timeOfTheDay = timeOfTheDay
        getUiState().timeOfTheDayOption.forEach { it.isSelected = it.option == timeOfTheDay }
        updatePrice()
    }

    private fun onTypeSelect(membershipType: MembershipType) {
        this.type = membershipType
        getUiState().typeOptions.forEach { it.isSelected = it.option == membershipType }
        updatePrice()
    }

    private fun onDurationSelect(membershipDuration: MembershipDuration) {
        this.duration = membershipDuration
        getUiState().durationOptions.forEach { it.isSelected = it.option == membershipDuration }
        updatePrice()
    }

    private fun updatePrice() {
        val price = 20_000*(getUiState().timeOfTheDayOption.first { it.isSelected }.option.ordinal+1) +
            4_500*(getUiState().typeOptions.first { it.isSelected }.option.ordinal+1) +
            15_000*(getUiState().durationOptions.first { it.isSelected }.option.ordinal+1)

        updateDataState { it.copy(price = NumberFormat.getIntegerInstance(Locale.getDefault()).format(price)) }
    }

    private fun listenSelectPlacesResult() {
        router.addResultListener<List<CityItemUiModel>>(PlaceSelectViewModel.PLACE_SELECT_RESULT+this.hashCode()) {
            handlePlaceSelectResult(it)
        }
    }

    private fun handlePlaceSelectResult(result: List<CityItemUiModel>) {
        userEntityProvider.userEntity = userEntityProvider.userEntity.copy(
            favouritePlacesIds = placesMapper.mapToIds(result),
            favouritePlacesText = placesMapper.mapSelectResult(result)
        )
        updateDataState { it.copy(favouritePlaces = userEntityProvider.userEntity.favouritePlacesText) }
    }

    private fun startPayment() {
        updateDataState { it.copy(paymentState = PaymentState.IN_PROGRESS) }

        ioScope.launch {
            Thread.sleep(3000L)

            if (duration == MembershipDuration.YEAR) {
                updateDataState { it.copy(paymentState = PaymentState.REJECTED) }
                return@launch
            }

            val result = interactor.createOrder(timeOfTheDay, type, duration)
            updateDataState { it.copy(
                currentMembership = mapper.mapMembership(result).description,
                paymentState = PaymentState.SUCCESS
            ) }
        }
    }

    private fun getNoMembershipString(): AnnotatedString {
        val base = resourceProvider.getString(R.string.no_membership)
        val p1 = resourceProvider.getString(R.string.no_membership_1)
        val p2 = resourceProvider.getString(R.string.no_membership_2)
        val p3 = resourceProvider.getString(R.string.no_membership_3)

        return mapper.getAnnotatedString(base, listOf(p1, p2, p3))
    }

    override fun onCleared() {
        super.onCleared()
        router.removeResultListener(PlaceSelectViewModel.PLACE_SELECT_RESULT)
    }
}