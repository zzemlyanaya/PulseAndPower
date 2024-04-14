package ru.zzemlyanaya.pulsepower.auth.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.zzemlyanaya.pulsepower.app.navigation.*
import ru.zzemlyanaya.pulsepower.auth.presentation.model.SignUpUiState
import ru.zzemlyanaya.pulsepower.core.viewModel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val router: NavigationRouter
) : BaseViewModel(router) {

    private val mutableUiState = mutableStateOf(SignUpUiState())
    val signUpUiState: State<SignUpUiState> = mutableUiState

    fun updateName(name: String) {
        mutableUiState.value = mutableUiState.value.copy(name = name)
    }

    fun updateSurname(surname: String) {
        mutableUiState.value = mutableUiState.value.copy(surname = surname)
    }

    fun updatePatronymic(patronymic: String) {
        mutableUiState.value = mutableUiState.value.copy(patronymic = patronymic)
    }

    fun updatePhone(phone: String) {
        mutableUiState.value = mutableUiState.value.copy(phone = phone)
    }

    fun onSignUpClick() {
        router.navigateTo(AuthDirections.phoneConfirm)
    }

    fun onSelectPlaces() {
        router.navigateTo(MainDirections.placeSelect)
    }
}