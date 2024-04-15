package ru.zzemlyanaya.pulsepower.placeSelect.presentation.mapping

import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.core.utils.ResourceProvider
import ru.zzemlyanaya.pulsepower.placeSelect.presentation.model.CityItemUiModel
import ru.zzemlyanaya.pulsepower.placeSelect.presentation.model.PlaceUiModel
import ru.zzemlyanaya.pulsepower.profile.domain.model.PlaceEntity
import ru.zzemlyanaya.pulsepower.profile.domain.model.UserEntityProvider
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

    private fun mapPlace(placeEntity: PlaceEntity) = PlaceUiModel(
        id = placeEntity.id,
        name = placeEntity.name,
        isSelected = userEntityProvider.userEntity.favouritePlacesIds.contains(placeEntity.id)
    )

    fun mapSelectResult(result: List<CityItemUiModel>): String {
        val base = StringBuilder("${result.first().name}, ${result.first().places.first().name}")

        if (result.size == 1) {
            if (result.first().places.size == 2) {
                base.append(", ${result.first().places[1].name}")
            }
            else {
                base.append(" и ещё ")
                base.append(resourceProvider.getPlurals(R.plurals.place, result.first().places.size - 1, result.first().places.size - 1))
            }
        } else if (result.size == 2 && result[1].places.size == 1) {
            base.append(" и ${result[1].name}, ${result[1].places.first().name}")
        } else {
            base.append(" и ещё ")
            base.append(resourceProvider.getPlurals(R.plurals.place, result.sumOf { it.places.size } - 1, result.sumOf { it.places.size } - 1))
            base.append(" в ")
            base.append(resourceProvider.getPlurals(R.plurals.city, result.size - 1, result.sumOf { it.places.size } - 1))
        }

        return base.toString()
    }

    fun mapToIds(cities: List<CityItemUiModel>) = cities.flatMap { it.places }.map { it.id }
}