package ru.zzemlyanaya.pulsepower.app.data.model.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
class ConfirmCodeRequest(
    val Code: String
) : Serializable