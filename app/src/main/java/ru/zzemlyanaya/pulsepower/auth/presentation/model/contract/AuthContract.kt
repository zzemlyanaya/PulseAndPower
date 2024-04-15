package ru.zzemlyanaya.pulsepower.auth.presentation.model.contract

import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent

sealed interface AuthContract {

    data class UiState(
        val phone: String = "",
        val phoneError: String? = null
    )

    sealed interface Intent : BaseIntent {
        class UpdatePhone(val phone: String) : Intent
        data object SignIn : Intent
    }
}