package ru.zzemlyanaya.pulsepower.auth.domain.interactor

import retrofit2.Response
import ru.zzemlyanaya.pulsepower.auth.data.repository.AuthRepository
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val repository: AuthRepository
) {

    suspend fun signIn(phone: String) = repository.signIn(phone)

    suspend fun getConfirmCode() = repository.getConfirmCode()

    suspend fun confirmCode(code: String) = repository.confirmCode(code)
}