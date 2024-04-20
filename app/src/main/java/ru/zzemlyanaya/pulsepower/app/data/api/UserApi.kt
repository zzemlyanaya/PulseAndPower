package ru.zzemlyanaya.pulsepower.app.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import ru.zzemlyanaya.pulsepower.app.data.model.request.CreateUserRequest
import ru.zzemlyanaya.pulsepower.app.data.model.request.SetPlacesRequest
import ru.zzemlyanaya.pulsepower.app.data.model.response.PlacesResponse
import ru.zzemlyanaya.pulsepower.app.data.model.response.UserResponse

interface UserApi {

    @PUT("user")
    suspend fun createUser(@Body request: CreateUserRequest): UserResponse

    @GET("user")
    suspend fun getUserInfo(): UserResponse

    @GET("user/favouritePlaces")
    suspend fun getFavouritePlaces(): PlacesResponse

    @POST("user/favouritePlaces")
    suspend fun setFavouritePlaces(@Body request: SetPlacesRequest): Response<Unit>
}