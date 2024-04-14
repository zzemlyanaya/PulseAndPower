package ru.zzemlyanaya.pulsepower.placeSelect.model

import java.io.Serializable

class CityItemUiModel(
    val name: String,
    val places: List<PlaceUiModel>,
    var isOpen: Boolean
) : Serializable {
    val hasSelected get() = places.any { it.isSelected }
}

class PlaceUiModel(
    val id: String,
    val name: String,
    var isSelected: Boolean,
) : Serializable