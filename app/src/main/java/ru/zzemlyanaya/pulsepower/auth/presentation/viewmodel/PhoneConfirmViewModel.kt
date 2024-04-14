package ru.zzemlyanaya.pulsepower.auth.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.zzemlyanaya.pulsepower.app.navigation.MainDirections
import ru.zzemlyanaya.pulsepower.app.navigation.NavigationRouter
import ru.zzemlyanaya.pulsepower.auth.domain.interactor.AuthInteractor
import ru.zzemlyanaya.pulsepower.core.viewModel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class PhoneConfirmViewModel @Inject constructor(
    private val interactor: AuthInteractor,
    private val router: NavigationRouter
) : BaseViewModel(router) {

    private val _code = mutableStateOf("")
    val code: State<String> = _code

    fun updateCode(code: String) {
        _code.value = code
        _baseUiState.value = _baseUiState.value.copy(error = null)
        validateCode(code)
    }

    private fun validateCode(code: String) {
        when {
            code.length < CODE_LENGTH -> return
            code.length == CODE_LENGTH -> sendCode(code)
            else -> _baseUiState.value = _baseUiState.value.copy(error = "")
        }
    }

    private fun sendCode(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _baseUiState.value = _baseUiState.value.copy(isLoading = true)
            val result = interactor.confirmCode(code)

            if (result.isSuccessful) {
                _baseUiState.value = _baseUiState.value.copy(isLoading = false)
                router.navigateTo(MainDirections.home)
            } else {
                _baseUiState.value = _baseUiState.value.copy(isLoading = false, error = "")
            }
        }
    }

    companion object {
        const val CODE_LENGTH = 4
    }
}