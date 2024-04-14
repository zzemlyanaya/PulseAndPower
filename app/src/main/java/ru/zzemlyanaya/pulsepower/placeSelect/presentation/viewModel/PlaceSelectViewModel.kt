package ru.zzemlyanaya.pulsepower.placeSelect.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.zzemlyanaya.pulsepower.app.navigation.NavigationRouter
import ru.zzemlyanaya.pulsepower.core.viewModel.BaseViewModel
import ru.zzemlyanaya.pulsepower.placeSelect.model.*
import javax.inject.Inject

@HiltViewModel
class PlaceSelectViewModel @Inject constructor(
    private val router: NavigationRouter
): BaseViewModel(router) {

    private val mutableUiState = mutableStateOf(PlacesUiState(getData()))
    val placesUiState: State<PlacesUiState> = mutableUiState

    fun onCityClick(city: CityItemUiModel) {
        mutableUiState.value.cities.find { it.name == city.name }?.apply { isOpen = !this.isOpen }
        mutableUiState.value = mutableUiState.value.copy(cUpdateFlag = mutableUiState.value.cUpdateFlag.not())
    }

    fun onPlaceClick(city: String, placeId: String) {
        mutableUiState.value.cities
            .find { it.name == city }?.places?.find { it.id == placeId }
            ?.apply { isSelected = !this.isSelected }
        mutableUiState.value = mutableUiState.value.copy(cUpdateFlag = mutableUiState.value.cUpdateFlag.not())
    }

    companion object {
        fun getData() =
            listOf(
                CityItemUiModel(
                    name = "Ад",
                    places = listOf(
                        PlaceUiModel("x1", "Power&Pulse Центр", false),
                        PlaceUiModel("x2", "Power&Pulse Дворцовая площадь", false),
                        PlaceUiModel("x3", "Power&Pulse Котлы", false)
                    ),
                    isOpen = false
                ),
                CityItemUiModel(
                    name = "Рай",
                    places = listOf(PlaceUiModel("x1", "Power&Pulse Центр", false)),
                    isOpen = false
                ),
                CityItemUiModel(
                    name = "Лимбо",
                    places = listOf(PlaceUiModel("x1", "Power&Pulse Центр", false)),
                    isOpen = false
                ),
                CityItemUiModel(
                    name = "Город Бесов",
                    places = listOf(
                        PlaceUiModel("x1", "Power&Pulse Сквер", false),
                        PlaceUiModel("x2", "Power&Pulse Опера", false)
                    ),
                    isOpen = false
                )
            )
    }
}