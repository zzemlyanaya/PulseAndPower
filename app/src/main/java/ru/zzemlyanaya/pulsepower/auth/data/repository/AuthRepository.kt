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

//    suspend fun signIn(phone: String) = api.signIn(SignInRequest(phone))

//    suspend fun getConfirmCode() = api.getConfirmCode()
//
//    suspend fun confirmCode(code: String) = api.confirmCode(ConfirmCodeRequest(code))

    suspend fun signIn(phone: String) = Response.success(Unit, Headers.headersOf("X-AuthSid", "e083becc-c4b2-4c93-a4a9-e59b7f716585"))

    suspend fun getConfirmCode(): Response<String> = Response.success("1234")

    suspend fun confirmCode(code: String): Response<Unit> {
        Thread.sleep(2000L)
        return if (code == "1234") Response.success(Unit) else Response.error(422, ResponseBody.create(null, "{}"))
    }

}