package ru.zzemlyanaya.pulsepower.profile.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class UserResponse(
    val id: String?,
    val firstName: String?,
    val lastName: String?,
    val patronymic: String?,
    val phone: String?,
    val favouritePlaces: Map<String, List<PlaceResponse>>?
) : Serializable