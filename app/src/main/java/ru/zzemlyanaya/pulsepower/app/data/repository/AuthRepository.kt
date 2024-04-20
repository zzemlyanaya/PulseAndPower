package ru.zzemlyanaya.pulsepower.app.data.repository

import ru.zzemlyanaya.pulsepower.app.data.api.AuthApi
import ru.zzemlyanaya.pulsepower.app.data.model.request.ConfirmCodeRequest
import ru.zzemlyanaya.pulsepower.app.data.model.request.SignInRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: AuthApi
) {

    suspend fun signIn(phone: String) = api.signIn(SignInRequest(phone))

    suspend fun getConfirmCode() = api.getConfirmCode()

    suspend fun confirmCode(code: String) = api.confirmCode(ConfirmCodeRequest(code))
}