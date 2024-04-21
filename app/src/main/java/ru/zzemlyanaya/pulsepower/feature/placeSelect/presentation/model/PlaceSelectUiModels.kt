package ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.model

import java.io.Serializable

data class CityItemUiModel(
    val name: String,
    val places: List<PlaceUiModel>,
    var isOpen: Boolean
) {
    val hasSelected get() = places.any { it.isSelected }
}

class PlaceUiModel(
    val id: String,
    val name: String,
    var isSelected: Boolean,
) : Serializable