package ru.zzemlyanaya.pulsepower.home.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class CreateOrderRequest(
    val TimeOfDay: String,
    val TrainingType: String,
    val Duration: Int
) : Serializable