package ru.zzemlyanaya.pulsepower.profile.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class PlacesResponse(
    val addresses: Map<String, List<PlaceResponse>>?
) : Serializable

@Keep
class PlaceResponse(
    val id: String?,
    val name: String?,
    val street: String?,
    val city: String?,
    val state: String?
) : Serializable