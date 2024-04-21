package ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.viewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.zzemlyanaya.pulsepower.app.navigation.NavigationRouter
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.core.utils.ResourceProvider
import ru.zzemlyanaya.pulsepower.core.viewModel.BaseViewModel
import ru.zzemlyanaya.pulsepower.feature.home.domain.interactor.StoreInteractor
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.mapping.PlacesUiMapper
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.model.CityItemUiModel
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.model.PlaceUiModel
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.model.contract.PlaceSelectContract
import ru.zzemlyanaya.pulsepower.feature.profile.domain.interactor.UserInteractor
import ru.zzemlyanaya.pulsepower.feature.profile.domain.model.UserEntityProvider
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PlaceSelectViewModel @Inject constructor(
    private val interactor: StoreInteractor,
    private val mapper: PlacesUiMapper,
    private val router: NavigationRouter
): BaseViewModel<PlaceSelectContract.UiState, PlaceSelectContract.Intent>(router) {

    override fun getInitialState() = PlaceSelectContract.UiState()

    init {
        ioScope.launch {
            showLoading()
            val cities = interactor.getAvailablePlaces()
            updateAndSetDataState { it.copy(cities = mapper.mapCities(cities)) }
        }
    }

    override fun handleIntent(intent: BaseIntent) {
        when (intent) {
            is PlaceSelectContract.Intent.CityClick -> onCityClick(intent.city)
            is PlaceSelectContract.Intent.PlaceClick -> onPlaceClick(intent.city, intent.placeId)
            else -> super.handleIntent(intent)
        }
    }

    private fun onCityClick(city: CityItemUiModel) {
        getUiState().cities.find { it.name == city.name }?.apply { isOpen = !this.isOpen }
        updateCities()
    }

    private fun onPlaceClick(city: String, placeId: String) {
        getUiState().cities
            .find { it.name == city }?.places
            ?.find { it.id == placeId }?.apply { isSelected = !isSelected }
        updateCities()
    }

    private fun updateCities() {
        updateAndSetDataState { it.copy(update = !it.update) }
    }

    override fun back() {
        val picked = getUiState().cities.sumOf { city -> city.places.count { it.isSelected } }
        if (picked == 0 || picked > 5) {
            updateDataState { it.copy(isError = true) }
            return
        }


        router.sendResultAll(
            getUiState().cities
                .filter { it.hasSelected }
                .map { city -> city.copy(places = city.places.filter { it.isSelected }) }
        )
        super.back()
    }

    companion object {
        val PLACE_SELECT_RESULT = UUID.randomUUID().hashCode()
    }
}