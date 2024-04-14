package ru.zzemlyanaya.pulsepower.auth.presentation.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import ru.zzemlyanaya.pulsepower.app.navigation.*
import ru.zzemlyanaya.pulsepower.auth.domain.interactor.AuthInteractor
import ru.zzemlyanaya.pulsepower.auth.presentation.model.AuthUiState
import ru.zzemlyanaya.pulsepower.auth.presentation.model.intent.AuthIntent
import ru.zzemlyanaya.pulsepower.core.model.BaseIntent
import ru.zzemlyanaya.pulsepower.core.viewModel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val interactor: AuthInteractor,
    private val router: NavigationRouter
) : BaseViewModel<AuthIntent>(router) {

    private val mutableUiState = mutableStateOf(AuthUiState())
    val authUiState: State<AuthUiState> = mutableUiState

    init {
        handleIntent()
    }

    override fun handleIntent(intent: BaseIntent) {
        when (intent) {
            is AuthIntent.UpdatePhone -> updatePhone(intent.phone)
            is AuthIntent.SignInClick -> onSingInClick()
            else -> super.handleIntent(intent)
        }
    }

    private fun updatePhone(input: String) {
        mutableUiState.value = mutableUiState.value.copy(phone = input)
        mutableUiState.value = mutableUiState.value.copy(phoneError = null)
    }

    private fun onSingInClick() {
        if (authUiState.value.phone.length != 11) {
            mutableUiState.value = mutableUiState.value.copy(phoneError = "Phone is not valid")
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                _baseUiState.value = _baseUiState.value.copy(isLoading = true)
                val result = interactor.signIn(authUiState.value.phone)

                if (result.isSuccessful) {
                    _baseUiState.value = _baseUiState.value.copy(isLoading = false)
                    result.headers()["X-AuthSid"]?.let {
                        handleAuthSid(it)
                        router.navigateTo(AuthDirections.phoneConfirm)
                    }
                    router.navigateTo(AuthDirections.phoneConfirm)
                } else {
                    _baseUiState.value = _baseUiState.value.copy(error = result.message())
                }
            }
        }
    }

    private fun handleAuthSid(sid: String) {
    }
}