package ru.zzemlyanaya.pulsepower.app.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.zzemlyanaya.pulsepower.app.data.model.request.CreateOrderRequest
import ru.zzemlyanaya.pulsepower.app.data.model.response.GetOrdersResponse
import ru.zzemlyanaya.pulsepower.app.data.model.response.OrderResponse
import ru.zzemlyanaya.pulsepower.app.data.model.response.PlacesResponse

interface StoreApi {

    @POST("store/order")
    suspend fun createOrder(@Body request: CreateOrderRequest): OrderResponse

    @GET("store/orders")
    suspend fun getAllOrders(): GetOrdersResponse

    @GET("store/places")
    suspend fun getAllPlaces(): PlacesResponse
}