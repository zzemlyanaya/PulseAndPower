package ru.zzemlyanaya.pulsepower.profile.domain.model

import java.io.Serializable

class PlaceEntity(
    val id: String,
    val name: String,
    val city: String
) : Serializable