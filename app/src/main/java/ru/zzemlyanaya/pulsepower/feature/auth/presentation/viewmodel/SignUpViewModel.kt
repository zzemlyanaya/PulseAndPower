package ru.zzemlyanaya.pulsepower.feature.auth.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.navigation.*
import ru.zzemlyanaya.pulsepower.feature.auth.presentation.model.contract.SignUpContract
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.core.utils.ResourceProvider
import ru.zzemlyanaya.pulsepower.core.viewModel.BaseViewModel
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.mapping.PlacesUiMapper
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.model.CityItemUiModel
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.viewModel.PlaceSelectViewModel.Companion.PLACE_SELECT_RESULT
import ru.zzemlyanaya.pulsepower.feature.profile.domain.interactor.UserInteractor
import ru.zzemlyanaya.pulsepower.feature.profile.domain.model.UserEntityProvider
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val placesMapper: PlacesUiMapper,
    private val interactor: UserInteractor,
    private val userProvider: UserEntityProvider,
    private val resourceProvider: ResourceProvider,
    private val router: NavigationRouter
) : BaseViewModel<SignUpContract.UiState, SignUpContract.Intent>(router) {

    override fun getInitialState() = SignUpContract.UiState()

    init {
        updateDataState { it.copy(favouritePlaces = userProvider.userEntity.favouritePlacesText) }
    }

    override fun handleIntent(intent: BaseIntent) {
        when (intent) {
            is SignUpContract.Intent.UpdateName -> updateName(intent.name)
            is SignUpContract.Intent.UpdateSurname -> updateSurname(intent.surname)
            is SignUpContract.Intent.UpdatePatronymic -> updatePatronymic(intent.patronymic)
            is SignUpContract.Intent.SelectPlaces -> onSelectPlaces()
            is SignUpContract.Intent.SignUp -> onSignUpClick()
            else -> super.handleIntent(intent)
        }
    }

    private fun updateName(name: String) {
        updateDataState { it.copy(name = name, nameError = null) }
    }

    private fun updateSurname(surname: String) {
        updateDataState { it.copy(surname = surname, surnameError = null) }
    }

    private fun updatePatronymic(patronymic: String) {
        updateDataState { it.copy(patronymic = patronymic, patronymicError = null) }
    }

    private fun onSelectPlaces() {
        router.addResultListener<List<CityItemUiModel>>(PLACE_SELECT_RESULT) {
            router.removeResultListener(PLACE_SELECT_RESULT)
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

    private fun onSignUpClick() {
        with(getUiState()) {
            if (name.isEmpty() || surname.isEmpty()) return

            ioScope.launch {
                showLoading()
                interactor.createUserInfo(
                    userProvider.userEntity.copy(
                        name = name,
                        surname = surname,
                        patronymic = patronymic
                    )
                )

                router.navigateTo(MainDirections.home)
            }
        }
    }
}