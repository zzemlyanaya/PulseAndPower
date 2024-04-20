package ru.zzemlyanaya.pulsepower.feature.auth.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.zzemlyanaya.pulsepower.app.navigation.*
import ru.zzemlyanaya.pulsepower.feature.auth.domain.interactor.AuthInteractor
import ru.zzemlyanaya.pulsepower.feature.auth.presentation.model.contract.CodeConfirmContract
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.core.viewModel.BaseViewModel
import ru.zzemlyanaya.pulsepower.feature.profile.domain.interactor.UserInteractor
import javax.inject.Inject

@HiltViewModel
class PhoneConfirmViewModel @Inject constructor(
    private val interactor: AuthInteractor,
    private val userInteractor: UserInteractor,
    private val router: NavigationRouter
) : BaseViewModel<CodeConfirmContract.UiState, CodeConfirmContract.Intent>(router) {

    override fun getInitialState() = CodeConfirmContract.UiState()

    override fun handleIntent(intent: BaseIntent) {
        when (intent) {
            is CodeConfirmContract.Intent.UpdateCode -> updateCode(intent.code)
            is CodeConfirmContract.Intent.EnterCode -> updateCode(router.getCurrentArgs<String>().orEmpty())
            else -> super.handleIntent(intent)
        }
    }

    private fun updateCode(code: String) {
        updateDataState { it.copy(code = code) }
        validateCode(code)
    }

    private fun validateCode(code: String) {
        when {
            code.length < CODE_LENGTH -> return
            code.length == CODE_LENGTH -> sendCode(code)
            else -> updateDataState { it.copy(codeError = "") }
        }
    }

    private fun sendCode(code: String) {
        ioScope.launch(Dispatchers.IO) {
            showLoading()
            val result = interactor.confirmCode(code)

            if (result.isSuccessful) {
                ioScope.launch {
                    val user = userInteractor.getUserInfo()

                    if (user.name.isEmpty() || user.surname.isEmpty()) router.navigateTo(AuthDirections.signUp)
                    else router.navigateTo(MainDirections.home)
                }
            } else {
                showError(result.message())
            }
        }
    }

    companion object {
        const val CODE_LENGTH = 4
    }
}