package ru.zzemlyanaya.pulsepower.feature.auth.domain.interactor

import ru.zzemlyanaya.pulsepower.app.data.repository.AuthRepository
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val repository: AuthRepository
) {

    suspend fun signIn(phone: String) = repository.signIn(phone)

    suspend fun getConfirmCode() = repository.getConfirmCode()

    suspend fun confirmCode(code: String) = repository.confirmCode(code)
}