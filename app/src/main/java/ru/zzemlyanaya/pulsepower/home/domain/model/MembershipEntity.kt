package ru.zzemlyanaya.pulsepower.home.domain.model

import ru.zzemlyanaya.pulsepower.history.domain.model.*
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

class MembershipEntity(
    val id: String,
    val expirationDate: String,
    val isActive: Boolean,
    val timeOfTheDay: TimeOfTheDay,
    val type: MembershipType,
    val duration: MembershipDuration
) : Serializable