package ru.zzemlyanaya.pulsepower.history.presentation.model

data class HistoryUiState(
    val items: List<SubscriptionUiModel> = emptyList()
) {
    val isEmpty get() = items.isEmpty()
}