package ru.zzemlyanaya.pulsepower.home.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.zzemlyanaya.pulsepower.home.data.model.*
import ru.zzemlyanaya.pulsepower.profile.data.model.PlacesResponse

interface StoreApi {

    @POST("store/order")
    suspend fun createOrder(@Body request: CreateOrderRequest): OrderResponse

    @GET("store/orders")
    suspend fun getAllOrders(): GetOrdersResponse

    @GET("store/places")
    suspend fun getAllPlaces(): PlacesResponse
}