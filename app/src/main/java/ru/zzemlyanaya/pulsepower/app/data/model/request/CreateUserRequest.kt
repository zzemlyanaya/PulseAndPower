package ru.zzemlyanaya.pulsepower.app.data.model.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
class CreateUserRequest(
    @SerializedName("FirstName")
    val name: String,
    @SerializedName("LastName")
    val surname: String,
    @SerializedName("Patronymic")
    val patronymic: String?,
    @SerializedName("FavouritePlaces")
    val places: List<String>
) : Serializable