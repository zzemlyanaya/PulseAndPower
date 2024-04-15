package ru.zzemlyanaya.pulsepower.core.viewModel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import ru.zzemlyanaya.pulsepower.app.navigation.NavigationRouter
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.core.contract.ScreenUiState

abstract class BaseViewModel<UiState, UiIntent : BaseIntent>(
    private val router: NavigationRouter
): ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        handleException(exception)
    }
    protected val ioScope = viewModelScope + Dispatchers.IO + coroutineExceptionHandler

    private val intentChannel = Channel<BaseIntent>(Channel.UNLIMITED)

    private val lastUiState: MutableStateFlow<UiState> = MutableStateFlow(getInitialState())
    private val _screenState = MutableStateFlow<ScreenUiState>(ScreenUiState.Data(getInitialState()))
    val screenState: StateFlow<ScreenUiState> = _screenState

    abstract fun getInitialState(): UiState

    init {
        observeIntent()
    }

    suspend fun sendIntent(intent: BaseIntent) {
        intentChannel.send(intent)
    }

    protected fun observeIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { handleIntent(it) }
        }
    }

    @CallSuper
    protected open fun handleIntent(intent: BaseIntent) {
        if (intent is BaseIntent.Back) back()
    }

    protected fun updateAndSetDataState(reducer: (UiState) -> UiState) {
        reducer(getUiState()).let {
            _screenState.value = ScreenUiState.Data(it)
            lastUiState.value = it
        }
    }

    protected fun updateDataState(reducer: (UiState) -> UiState) {
        if (_screenState.value !is ScreenUiState.Data<*>) return

        reducer((_screenState.value as ScreenUiState.Data<UiState>).state).let {
            _screenState.value = ScreenUiState.Data(it)
            lastUiState.value = it
        }
    }

    protected fun showError(error: String) {
        _screenState.value = ScreenUiState.Error(error)
    }

    protected fun showLoading() {
        _screenState.value = ScreenUiState.Loading(getUiState())
    }

    protected fun getUiState(): UiState {
        return if (_screenState.value is ScreenUiState.Data<*>) {
            (_screenState.value as ScreenUiState.Data<UiState>).state
        } else {
            lastUiState.value
        }
    }

    protected open fun handleException(e: Throwable) {
        e.printStackTrace()
        showError(e.message ?: "Что-то пошло не так, но мы уже работает над этим!")
    }

    protected open fun back() {
        router.back()
    }
}