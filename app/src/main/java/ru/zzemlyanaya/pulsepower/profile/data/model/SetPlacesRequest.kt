package ru.zzemlyanaya.pulsepower.profile.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class SetPlacesRequest(
    val PlaceIds: List<String>
) : Serializable