package ru.zzemlyanaya.pulsepower.auth.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
class ConfirmCodeRequest(
    val Code: String
) : Serializable