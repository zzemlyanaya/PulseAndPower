package ru.zzemlyanaya.pulsepower.home.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class GetOrdersResponse(
    val orders: List<OrderResponse>?
) : Serializable