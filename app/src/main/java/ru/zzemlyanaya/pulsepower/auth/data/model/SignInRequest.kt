package ru.zzemlyanaya.pulsepower.auth.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
class SignInRequest(
    @SerializedName("NormalizedPhone")
    val phone: String
) : Serializable