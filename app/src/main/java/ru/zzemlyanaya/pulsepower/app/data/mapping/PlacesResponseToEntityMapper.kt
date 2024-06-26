package ru.zzemlyanaya.pulsepower.app.data.mapping

import ru.zzemlyanaya.pulsepower.app.data.model.request.SetPlacesRequest
import ru.zzemlyanaya.pulsepower.app.data.model.response.PlaceResponse
import ru.zzemlyanaya.pulsepower.app.data.model.response.PlacesResponse
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.mapping.PlacesUiMapper
import ru.zzemlyanaya.pulsepower.feature.profile.domain.model.PlaceEntity
import javax.inject.Inject

class PlacesResponseToEntityMapper @Inject constructor(
    private val textMapper: PlacesUiMapper,
) {

    fun mapPlacesResponse(response: PlacesResponse) =
        response.addresses.orEmpty().mapValues { it.value.map(::mapPlaceResponse) }

    fun mapPlacesResponse(response: Map<String, List<PlaceResponse>>) =
        response.flatMap { (_, places) -> places.map { it.id.orEmpty() } }

    private fun mapPlaceResponse(response: PlaceResponse) = PlaceEntity(
        id = response.id.orEmpty(),
        name = response.name.orEmpty(),
        city = response.city.orEmpty()
    )

    fun mapPlacesResponseToText(response: Map<String, List<PlaceResponse>>) =
        if (response.isEmpty()) ""
        else textMapper.mapSelectResult(textMapper.mapFavouriteCities(response.mapValues { it.value.map(::mapPlaceResponse) }))
}