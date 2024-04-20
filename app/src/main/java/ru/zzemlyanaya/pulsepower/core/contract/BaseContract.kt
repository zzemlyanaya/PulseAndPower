package ru.zzemlyanaya.pulsepower.core.contract

interface BaseIntent {
    data object Back : BaseIntent
}

sealed interface ScreenUiState {
    data object Idle : ScreenUiState
    data class Error<UiState>(val error: String, val state: UiState) : ScreenUiState
    data class Loading<UiState>(val state: UiState) : ScreenUiState
    data class Data<UiState>(val state: UiState) : ScreenUiState
}