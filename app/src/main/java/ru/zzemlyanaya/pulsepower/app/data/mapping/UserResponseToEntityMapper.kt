package ru.zzemlyanaya.pulsepower.app.data.mapping

import ru.zzemlyanaya.pulsepower.app.data.model.request.CreateUserRequest
import ru.zzemlyanaya.pulsepower.app.data.model.response.UserResponse
import ru.zzemlyanaya.pulsepower.feature.profile.domain.model.UserEntity
import javax.inject.Inject

class UserResponseToEntityMapper @Inject constructor(
    private val placesMapper: PlacesResponseToEntityMapper
) {

    fun mapUserResponse(response: UserResponse) = UserEntity(
        id = response.id.orEmpty(),
        name = response.firstName.orEmpty(),
        surname = response.lastName.orEmpty(),
        patronymic = response.patronymic.orEmpty(),
        phone = response.phone.orEmpty(),
        favouritePlacesIds = placesMapper.mapPlacesResponse(response.favouritePlaces.orEmpty()),
        favouritePlacesText = placesMapper.mapPlacesResponseToText(response.favouritePlaces.orEmpty())
    )

    fun mapUserRequest(user: UserEntity) = CreateUserRequest(
        FirstName = user.name,
        LastName = user.surname,
        Patronimic = user.patronymic,
        FavouritePlaces = user.favouritePlacesIds
    )

}