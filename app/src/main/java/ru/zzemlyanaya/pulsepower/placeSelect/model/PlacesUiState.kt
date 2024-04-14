package ru.zzemlyanaya.pulsepower.placeSelect.model

import androidx.annotation.StringRes
import java.io.Serializable
import ru.zzemlyanaya.pulsepower.R

data class PlacesUiState(
    val cities: List<CityItemUiModel> = emptyList(),
    val cUpdateFlag: Boolean = false,
    val isError: Boolean = false,
    @StringRes val title: Int = R.string.places_select
)
