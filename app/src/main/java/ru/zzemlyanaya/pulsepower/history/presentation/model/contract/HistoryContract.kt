package ru.zzemlyanaya.pulsepower.history.presentation.model.contract

import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.history.presentation.model.MembershipUiModel

sealed interface HistoryContract {

    data class UiState(
        val items: List<MembershipUiModel> = emptyList()
    ) {
        val isEmpty get() = items.isEmpty()
    }

    sealed interface Intent : BaseIntent {
        data class RepeatSubscription(val id: String) : Intent
    }
}