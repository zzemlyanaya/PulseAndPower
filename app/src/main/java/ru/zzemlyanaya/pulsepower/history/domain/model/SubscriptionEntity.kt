package ru.zzemlyanaya.pulsepower.history.domain.model

import java.io.Serializable
import java.time.LocalDateTime

class OrderEntity(
    val id: String,
    val date: LocalDateTime,
    val subscription: SubscriptionEntity
) : Serializable

class SubscriptionEntity(
    val timeOfDay: TimeOfTheDay,
    val type: MembershipType,
    val period: Int
) : Serializable