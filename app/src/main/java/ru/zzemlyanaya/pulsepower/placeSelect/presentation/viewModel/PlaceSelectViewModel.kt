package ru.zzemlyanaya.pulsepower.placeSelect.presentation.viewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.zzemlyanaya.pulsepower.app.navigation.NavigationRouter
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.core.utils.ResourceProvider
import ru.zzemlyanaya.pulsepower.core.viewModel.BaseViewModel
import ru.zzemlyanaya.pulsepower.home.domain.interactor.StoreInteractor
import ru.zzemlyanaya.pulsepower.placeSelect.presentation.mapping.PlacesUiMapper
import ru.zzemlyanaya.pulsepower.placeSelect.presentation.model.CityItemUiModel
import ru.zzemlyanaya.pulsepower.placeSelect.presentation.model.PlaceUiModel
import ru.zzemlyanaya.pulsepower.placeSelect.presentation.model.contract.PlaceSelectContract
import ru.zzemlyanaya.pulsepower.profile.domain.interactor.UserInteractor
import ru.zzemlyanaya.pulsepower.profile.domain.model.UserEntityProvider
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PlaceSelectViewModel @Inject constructor(
    private val interactor: StoreInteractor,
    private val mapper: PlacesUiMapper,
    private val router: NavigationRouter
): BaseViewModel<PlaceSelectContract.UiState, PlaceSelectContract.Intent>(router) {

    private val cities = mutableListOf<CityItemUiModel>()

    override fun getInitialState() = PlaceSelectContract.UiState()

    init {
        ioScope.launch {
            showLoading()
            cities.addAll(mapper.mapCities(interactor.getAvailablePlaces()))
            updateCities()
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
        cities.find { it.name == city.name }?.apply { isOpen = !this.isOpen }
        updateCities()
    }

    private fun onPlaceClick(city: String, placeId: String) {
        cities.find { it.name == city }
            ?.places?.forEach {
                if (it.id == placeId) it.isSelected = !it.isSelected
                else it.isSelected = false
            }
        updateCities()
    }

    private fun updateCities() {
        updateAndSetDataState { it.copy(cities = cities, update = !it.update) }
    }

    override fun back() {
        val picked = cities.sumOf { city -> city.places.count { it.isSelected } }
        if (picked == 0 || picked > 5) {
            updateDataState { it.copy(isError = true) }
            return
        }

        router.sendResult(PLACE_SELECT_RESULT, cities.filter { it.hasSelected })
        super.back()
    }

    companion object {
        val PLACE_SELECT_RESULT = UUID.randomUUID().hashCode()
    }
}