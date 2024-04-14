package ru.zzemlyanaya.pulsepower.home.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.zzemlyanaya.pulsepower.app.navigation.NavigationRouter
import ru.zzemlyanaya.pulsepower.core.viewModel.BaseViewModel
import ru.zzemlyanaya.pulsepower.history.domain.model.*
import ru.zzemlyanaya.pulsepower.home.domain.interactor.HomeInteractor
import ru.zzemlyanaya.pulsepower.home.presentation.model.HomeUiState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val interactor: HomeInteractor,
    private val router: NavigationRouter
) : BaseViewModel(router) {

    private val mutableUiState = mutableStateOf(HomeUiState())
    val homeUiState: State<HomeUiState> = mutableUiState

    init {
        mutableUiState.value = mutableUiState.value.copy(
            timeOfTheDayOption = interactor.getTimeOptions(),
            typeOptions = interactor.getTypeOptions(),
            durationOptions = interactor.getDurationOptions()
        )
    }

    fun onTimeOfTheDaySelect(timeOfTheDay: TimeOfTheDay) {
        mutableUiState.value.timeOfTheDayOption.forEach {
            it.isSelected = it.option == timeOfTheDay
        }
        forceUpdate()
    }

    fun onTypeSelect(membershipType: MembershipType) {
        mutableUiState.value.typeOptions.forEach {
            it.isSelected = it.option == membershipType
        }
        forceUpdate()
    }

    fun onDurationSelect(membershipDuration: MembershipDuration) {
        mutableUiState.value.durationOptions.forEach {
            it.isSelected = it.option == membershipDuration
        }
        forceUpdate()
    }

    private fun forceUpdate() {
        mutableUiState.value = mutableUiState.value.copy(updateFlag = !mutableUiState.value.updateFlag)
    }
}