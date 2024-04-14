package ru.zzemlyanaya.pulsepower.auth.presentation.model.intent

import ru.zzemlyanaya.pulsepower.core.model.BaseIntent

sealed interface AuthIntent : BaseIntent {
    class UpdatePhone(val phone: String) : AuthIntent
    data object SignInClick : AuthIntent
}