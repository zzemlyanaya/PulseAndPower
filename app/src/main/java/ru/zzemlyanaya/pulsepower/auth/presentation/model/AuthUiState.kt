package ru.zzemlyanaya.pulsepower.auth.presentation.model

data class AuthUiState(
    val phone: String = "",
    val phoneError: String? = null
)
