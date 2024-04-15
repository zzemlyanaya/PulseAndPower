package ru.zzemlyanaya.pulsepower.profile.data.repository

import ru.zzemlyanaya.pulsepower.placeSelect.data.mapping.PlacesResponseToEntityMapper
import ru.zzemlyanaya.pulsepower.profile.data.api.UserApi
import ru.zzemlyanaya.pulsepower.profile.data.mapping.UserResponseToEntityMapper
import ru.zzemlyanaya.pulsepower.profile.data.model.SetPlacesRequest
import ru.zzemlyanaya.pulsepower.profile.domain.model.PlaceEntity
import ru.zzemlyanaya.pulsepower.profile.domain.model.UserEntity
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val userApi: UserApi,
    private val mapper: UserResponseToEntityMapper,
    private val placesMapper: PlacesResponseToEntityMapper
) {

    suspend fun createUserInfo(userEntity: UserEntity) =
        mapper.mapUserResponse(userApi.createUser(mapper.mapUserRequest(userEntity)))

    suspend fun getUserInfo(): UserEntity = mapper.mapUserResponse(userApi.getUserInfo())

    suspend fun setFavouritePlaces(places: List<String>) = userApi.setFavouritePlaces(SetPlacesRequest(PlaceIds = places))
}