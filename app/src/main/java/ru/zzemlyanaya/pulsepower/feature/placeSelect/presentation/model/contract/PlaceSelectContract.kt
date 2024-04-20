package ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.model.contract

import androidx.annotation.StringRes
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.model.CityItemUiModel

sealed interface PlaceSelectContract {

    data class UiState(
        val cities: List<CityItemUiModel> = emptyList(),
        val isError: Boolean = false,
        val update: Boolean = false,
        @StringRes val title: Int = R.string.places_select
    )

    sealed interface Intent : BaseIntent {
        data class CityClick(val city: CityItemUiModel) : Intent
        data class PlaceClick(val city: String, val placeId: String) : Intent
    }
}