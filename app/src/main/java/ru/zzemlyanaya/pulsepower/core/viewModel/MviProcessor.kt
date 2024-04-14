package ru.zzemlyanaya.pulsepower.core.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.zzemlyanaya.pulsepower.core.model.*

abstract class MviProcessor<S : State, I : Intent, E : SingleEvent> : ViewModel() {

    private val _intent: MutableSharedFlow<I> = MutableSharedFlow()
    private val initialState: S by lazy { initialState() }
    private val _viewState: MutableStateFlow<S> by lazy { MutableStateFlow(initialState) }
    val viewState: StateFlow<S> = _viewState

    protected abstract val reducer: Reducer<S, I>

    protected abstract fun initialState(): S

    init {
        subscribeToIntents()
    }

    fun sendIntent(intent: I) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    private fun subscribeToIntents() {
        viewModelScope.launch {
            _intent.collect {
                reduceInternal(_viewState.value, it)
                launch {
                    handleIntent(it, _viewState.value)?.let { newIntent -> sendIntent(newIntent) }
                }
            }
        }
    }

    private fun reduceInternal(prevState: S, intent: I) {
        setState { reducer.reduce(prevState, intent) }
    }

    protected abstract suspend fun handleIntent(intent: I, state: S): I?

    private fun setState(reducer: (S) -> S) {
        _viewState.value = reducer(_viewState.value)
    }

    interface Reducer<S, I> {
        fun reduce(state: S, intent: I): S
    }
}
