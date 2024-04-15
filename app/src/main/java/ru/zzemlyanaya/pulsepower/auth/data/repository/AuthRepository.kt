package ru.zzemlyanaya.pulsepower.auth.data.repository

import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.Response
import ru.zzemlyanaya.pulsepower.auth.data.api.AuthApi
import ru.zzemlyanaya.pulsepower.auth.data.model.ConfirmCodeRequest
import ru.zzemlyanaya.pulsepower.auth.data.model.SignInRequest
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