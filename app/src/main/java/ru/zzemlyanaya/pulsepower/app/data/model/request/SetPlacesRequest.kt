package ru.zzemlyanaya.pulsepower.app.data.model.request

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class SetPlacesRequest(
    val PlaceIds: List<String>
) : Serializable