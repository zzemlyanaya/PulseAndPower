package ru.zzemlyanaya.pulsepower.feature.auth.presentation.model.contract

import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent

sealed interface CodeConfirmContract {
    data class UiState(
        val code: String = "",
        val codeError: String? = null
    )

    sealed interface Intent : BaseIntent {
        data class UpdateCode(val code: String) : Intent
        data object EnterCode : Intent
    }
}