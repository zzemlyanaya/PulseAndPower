package ru.zzemlyanaya.pulsepower.core.viewModel

import androidx.annotation.CallSuper
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import ru.zzemlyanaya.pulsepower.app.navigation.NavigationRouter
import ru.zzemlyanaya.pulsepower.core.model.BaseIntent
import ru.zzemlyanaya.pulsepower.core.model.BaseUiState

abstract class BaseViewModel<T : BaseIntent>(
    private val router: NavigationRouter
): ViewModel() {

    private val intentChannel = Channel<T>(Channel.UNLIMITED)

    protected val _baseUiState = mutableStateOf(BaseUiState())
    val baseUiState: State<BaseUiState> = _baseUiState

    init {
        handleIntent()
    }

    suspend fun sendIntent(intent: T) {
        intentChannel.send(intent)
    }

    @CallSuper
    protected fun handleIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { handleIntent(it) }
        }
    }

    protected open fun handleIntent(intent: BaseIntent) {
        if (intent is BaseIntent.Back) back()
    }

    private fun back() {
        router.back()
    }
}