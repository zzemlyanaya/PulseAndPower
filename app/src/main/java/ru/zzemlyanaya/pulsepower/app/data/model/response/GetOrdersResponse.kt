package ru.zzemlyanaya.pulsepower.app.data.model.response

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class GetOrdersResponse(
    val orders: List<OrderResponse>?
) : Serializable