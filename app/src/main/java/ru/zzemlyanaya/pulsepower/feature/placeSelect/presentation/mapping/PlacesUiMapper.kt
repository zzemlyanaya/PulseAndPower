package ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.mapping

import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.core.utils.ResourceProvider
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.model.CityItemUiModel
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.model.PlaceUiModel
import ru.zzemlyanaya.pulsepower.feature.profile.domain.model.PlaceEntity
import ru.zzemlyanaya.pulsepower.feature.profile.domain.model.UserEntityProvider
import java.lang.StringBuilder
import javax.inject.Inject

class PlacesUiMapper @Inject constructor(
    private val userEntityProvider: UserEntityProvider,
    private val resourceProvider: ResourceProvider
) {

    fun mapCities(cities: Map<String, List<PlaceEntity>>) = cities.map { (key, value) ->
        CityItemUiModel(
            name = key,
            places = value.map(::mapPlace),
            isOpen = false
        )
    }

    fun mapFavouriteCities(cities: Map<String, List<PlaceEntity>>) = cities.map { (key, value) ->
        CityItemUiModel(
            name = key,
            places = value.map(::mapFavouritePlace),
            isOpen = false
        )
    }

    private fun mapPlace(placeEntity: PlaceEntity) = PlaceUiModel(
        id = placeEntity.id,
        name = placeEntity.name,
        isSelected = userEntityProvider.userEntity.favouritePlacesIds.contains(placeEntity.id)
    )

    private fun mapFavouritePlace(placeEntity: PlaceEntity) = PlaceUiModel(
        id = placeEntity.id,
        name = placeEntity.name,
        isSelected = true
    )

    fun mapSelectResult(result: List<CityItemUiModel>): String {
        val base = StringBuilder("${result[0].name}, ${result[0].places[0].name}")

        val citiesCount = result.size
        val placesCount = result.sumOf { city -> city.places.count { it.isSelected } }

        when {
            citiesCount == 1 && placesCount == 2 -> base.append(", ${result[0].places[1].name}")
            citiesCount == 1 && placesCount > 2 ->
                base
                    .append(" ${resourceProvider.getString(R.string.and)} ")
                    .append(resourceProvider.getPlurals(R.plurals.place, placesCount-1, placesCount-1))
            citiesCount == 2 && placesCount == 2 -> base.append("; ${result[1].name}, ${result[1].places[0].name}")
            citiesCount > 1 -> {
                base
                    .append(" ${resourceProvider.getString(R.string.and)} ")
                    .append(resourceProvider.getPlurals(R.plurals.place, placesCount-1, placesCount-1))
                    .append(" ${resourceProvider.getString(R.string.in_cities)} ")
                    .append(resourceProvider.getPlurals(R.plurals.city, citiesCount-1, citiesCount-1))
            }
        }

        return base.toString()
    }

    fun mapToIds(cities: List<CityItemUiModel>) = cities.map { it.places.map { it.id } }.flatten()
}