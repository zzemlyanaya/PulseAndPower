package ru.zzemlyanaya.pulsepower.placeSelect.data.mapping

import ru.zzemlyanaya.pulsepower.placeSelect.presentation.mapping.PlacesUiMapper
import ru.zzemlyanaya.pulsepower.profile.data.model.*
import ru.zzemlyanaya.pulsepower.profile.domain.model.PlaceEntity
import javax.inject.Inject

class PlacesResponseToEntityMapper @Inject constructor(
    private val textMapper: PlacesUiMapper,
) {

    fun mapPlacesResponse(response: PlacesResponse) =
        response.addresses.orEmpty().mapValues { it.value.map(::mapPlaceResponse) }

    fun mapPlacesResponse(response: Map<String, List<PlaceResponse>>) = response
        .map { (_, value) -> value.map { it.id.orEmpty() } }
        .flatten()

    fun mapPlacesRequest(places: List<PlaceEntity>) = SetPlacesRequest(
        PlaceIds = places.map { it.id }
    )

    fun mapPlaceResponse(response: PlaceResponse) = PlaceEntity(
        id = response.id.orEmpty(),
        name = response.name.orEmpty(),
        city = response.city.orEmpty()
    )

    fun mapPlacesResponseToText(response: Map<String, List<PlaceResponse>>) =
        if (response.isEmpty()) ""
        else textMapper.mapSelectResult(textMapper.mapCities(response.mapValues { it.value.map(::mapPlaceResponse) }))
}