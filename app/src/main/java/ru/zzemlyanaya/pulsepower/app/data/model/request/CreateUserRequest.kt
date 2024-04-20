package ru.zzemlyanaya.pulsepower.app.data.model.request

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class CreateUserRequest(
    val FirstName: String,
    val LastName: String,
    val Patronimic: String?,
    val FavouritePlaces: List<String>
) : Serializable