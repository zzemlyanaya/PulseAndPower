package ru.zzemlyanaya.pulsepower.feature.home.domain.model

import ru.zzemlyanaya.pulsepower.feature.history.domain.model.*
import java.io.Serializable

class MembershipEntity(
    val id: String,
    val expirationDate: String,
    val isActive: Boolean,
    val timeOfTheDay: TimeOfTheDay,
    val type: MembershipType,
    val duration: MembershipDuration
) : Serializable