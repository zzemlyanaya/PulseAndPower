package ru.zzemlyanaya.pulsepower.app.data.model.response

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class OrderResponse(
    val id: String?,
    val date: String?,
    val isExpired: Boolean?,
    val subscription: SubscriptionResponse?
) : Serializable

@Keep
class SubscriptionResponse(
    val timeOfDay: String,
    val options: String?,
    val duration: Int?
) : Serializable