package ru.zzemlyanaya.pulsepower.feature.profile.presentation.viewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.navigation.MainDirections
import ru.zzemlyanaya.pulsepower.app.navigation.NavigationRouter
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.core.utils.ResourceProvider
import ru.zzemlyanaya.pulsepower.core.viewModel.BaseViewModel
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.mapping.PlacesUiMapper
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.model.CityItemUiModel
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.viewModel.PlaceSelectViewModel
import ru.zzemlyanaya.pulsepower.feature.profile.domain.interactor.UserInteractor
import ru.zzemlyanaya.pulsepower.feature.profile.domain.model.UserEntityProvider
import ru.zzemlyanaya.pulsepower.feature.profile.presentation.model.contract.UserInfoContract
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val placesMapper: PlacesUiMapper,
    private val interactor: UserInteractor,
    private val userProvider: UserEntityProvider,
    private val resourceProvider: ResourceProvider,
    private val router: NavigationRouter
) : BaseViewModel<UserInfoContract.UiState, UserInfoContract.Intent>(router) {

    private var oldPlaces = emptyList<String>()
    private var oldPlacesText = ""

    override fun getInitialState() = UserInfoContract.UiState()

    init {
        oldPlaces = userProvider.userEntity.favouritePlacesIds
        oldPlacesText = userProvider.userEntity.favouritePlacesText

        updateDataState { it.copy(
            name = userProvider.userEntity.name,
            surname = userProvider.userEntity.surname,
            patronymic = userProvider.userEntity.patronymic.takeIf { it.isNotEmpty() } ?: resourceProvider.getString(R.string.patronymic_absent),
            phone = userProvider.userEntity.phone,
            favouritePlaces = userProvider.userEntity.favouritePlacesText
        ) }
    }

    override fun handleIntent(intent: BaseIntent) {
        when (intent) {
            is UserInfoContract.Intent.ChangeFavouritePlaces -> onSelectPlaces()
            is UserInfoContract.Intent.SaveChanges -> saveAndBack()
            else -> super.handleIntent(intent)
        }
    }

    private fun onSelectPlaces() {
        router.addResultListener<List<CityItemUiModel>>(PlaceSelectViewModel.PLACE_SELECT_RESULT+this.hashCode()) {
            router.removeResultListener(PlaceSelectViewModel.PLACE_SELECT_RESULT)
            handlePlaceSelectResult(it)
        }

        router.navigateTo(MainDirections.placeSelect)
    }

    private fun handlePlaceSelectResult(result: List<CityItemUiModel>) {
        userProvider.userEntity = userProvider.userEntity.copy(
            favouritePlacesIds = placesMapper.mapToIds(result),
            favouritePlacesText = placesMapper.mapSelectResult(result)
        )
        updateDataState { it.copy(favouritePlaces = userProvider.userEntity.favouritePlacesText) }
    }

    private fun saveAndBack() {
        ioScope.launch {
            showLoading()
            val result = interactor.setFavouritePlaces(userProvider.userEntity.favouritePlacesIds)
            if (result.isSuccessful) {
                router.back()
            } else {
                showError(result.message())
            }
        }
    }

    override fun back() {
        userProvider.userEntity = userProvider.userEntity.copy(
            favouritePlacesIds = oldPlaces,
            favouritePlacesText = oldPlacesText
        )
        super.back()
    }
}