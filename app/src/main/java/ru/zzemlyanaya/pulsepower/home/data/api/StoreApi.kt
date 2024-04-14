package ru.zzemlyanaya.pulsepower.home.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import ru.zzemlyanaya.pulsepower.home.data.model.*
import ru.zzemlyanaya.pulsepower.profile.data.model.PlacesResponse

interface StoreApi {

    @GET("store/order")
    fun createOrder(@Body request: CreateOrderRequest): Response<OrderResponse>

    @GET("store/orders")
    fun getAllOrders(): Response<GetOrdersResponse>

    @GET("store/places")
    fun getAllPlaces(): Response<PlacesResponse>
}