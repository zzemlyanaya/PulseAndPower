package ru.zzemlyanaya.pulsepower.home.data.repository

import ru.zzemlyanaya.pulsepower.history.domain.model.*
import ru.zzemlyanaya.pulsepower.home.data.api.StoreApi
import ru.zzemlyanaya.pulsepower.home.data.mapping.StoreResponseToEntityMapper
import ru.zzemlyanaya.pulsepower.home.data.model.CreateOrderRequest
import ru.zzemlyanaya.pulsepower.home.domain.cache.UserMembershipCache
import ru.zzemlyanaya.pulsepower.placeSelect.data.mapping.PlacesResponseToEntityMapper
import ru.zzemlyanaya.pulsepower.placeSelect.presentation.model.CityItemUiModel
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val api: StoreApi,
    private val cache: UserMembershipCache,
    private val mapper: StoreResponseToEntityMapper,
    private val placesMapper: PlacesResponseToEntityMapper
){

    suspend fun getAllMemberships() =
        cache.getAll().takeUnless { it.isEmpty() } ?: mapper.mapOrders(api.getAllOrders()).also { cache.set(it) }

    suspend fun getActiveMembership() = cache.getActive().takeUnless { cache.memberships.isEmpty() } ?: getAllMemberships().firstOrNull { it.isActive }

    suspend fun getAllPlaces() = placesMapper.mapPlacesResponse(api.getAllPlaces())

    suspend fun createOrder(time: TimeOfTheDay, type: MembershipType, duration: MembershipDuration)
        = mapper.mapMembership(api.createOrder(CreateOrderRequest(time.name, type.name, duration.months))).also { cache.add(it) }
}