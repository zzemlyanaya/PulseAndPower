package ru.zzemlyanaya.pulsepower.app.data.model.request

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class CreateOrderRequest(
    val TimeOfDay: String,
    val TrainingType: String,
    val Duration: Int
) : Serializable