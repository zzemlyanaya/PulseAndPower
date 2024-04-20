package ru.zzemlyanaya.pulsepower.feature.auth.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.zzemlyanaya.pulsepower.app.navigation.*
import ru.zzemlyanaya.pulsepower.feature.auth.domain.interactor.AuthInteractor
import ru.zzemlyanaya.pulsepower.feature.auth.presentation.model.contract.AuthContract
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.core.http.AuthCredentialsCache
import ru.zzemlyanaya.pulsepower.core.viewModel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val interactor: AuthInteractor,
    private val authCredentialsCache: AuthCredentialsCache,
    private val router: NavigationRouter
) : BaseViewModel<AuthContract.UiState, AuthContract.Intent>(router) {

    override fun getInitialState() = AuthContract.UiState()

    override fun handleIntent(intent: BaseIntent) {
        when (intent) {
            is AuthContract.Intent.UpdatePhone -> updatePhone(intent.phone)
            is AuthContract.Intent.SignIn -> onSingInClick(getUiState().phone)
            else -> super.handleIntent(intent)
        }
    }

    private fun updatePhone(input: String) {
        updateDataState { it.copy(phone = input, phoneError = null) }
    }

    private fun onSingInClick(phone: String) {
        if (phone.length != 10) {
            updateDataState { it.copy(phoneError = "Phone is not valid") }
        } else {
            ioScope.launch {
                showLoading()
                val result = interactor.signIn(phone)

                if (result.isSuccessful) {
                    result.headers()["X-AuthSid"]?.let {
                        handleAuthSid(it)

                        ioScope.launch {
                            val code = interactor.getConfirmCode()
                            if (code.isSuccessful) {
                                hideLoading()
                                result.headers()["X-AuthSid"]?.let { sid -> handleAuthSid(sid) }
                                router.navigateTo(AuthDirections.phoneConfirm(code.body().orEmpty()))
                            } else {
                                showError(code.message())
                            }
                        }
                    }
                } else {
                    showError(result.message())
                }
            }
        }
    }

    private fun handleAuthSid(sid: String) {
        authCredentialsCache.authSid = sid
    }
}