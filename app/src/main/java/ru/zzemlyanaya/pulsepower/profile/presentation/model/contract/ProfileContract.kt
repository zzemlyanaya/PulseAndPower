package ru.zzemlyanaya.pulsepower.profile.presentation.model.contract

import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent

sealed interface ProfileContract {
    data object UiState

    sealed interface Intent : BaseIntent {
        data object OpenMyInfo : Intent
        data object OpenHistory : Intent
    }
}