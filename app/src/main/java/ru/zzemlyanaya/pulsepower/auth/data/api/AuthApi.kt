package ru.zzemlyanaya.pulsepower.auth.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import ru.zzemlyanaya.pulsepower.auth.data.model.*

interface AuthApi {

    @POST("auth/sendCode")
    suspend fun signIn(@Body request: SignInRequest): Response<Unit>

    @GET("test/verificationCode")
    suspend fun getConfirmCode(): Response<String>

    @POST("auth/confirmCode")
    suspend fun confirmCode(@Body request: ConfirmCodeRequest): Response<Unit>
}