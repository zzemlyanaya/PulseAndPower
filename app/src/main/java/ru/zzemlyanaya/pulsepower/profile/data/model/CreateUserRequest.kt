package ru.zzemlyanaya.pulsepower.profile.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class CreateUserRequest(
    val FirstName: String,
    val LastName: String,
    val Patronimic: String?,
    val FavouritePlaces: List<String>
) : Serializable