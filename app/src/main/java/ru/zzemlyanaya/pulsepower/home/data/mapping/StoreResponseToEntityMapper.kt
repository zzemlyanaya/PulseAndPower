package ru.zzemlyanaya.pulsepower.home.data.mapping

import android.icu.text.SimpleDateFormat
import ru.zzemlyanaya.pulsepower.history.domain.model.*
import ru.zzemlyanaya.pulsepower.home.data.model.GetOrdersResponse
import ru.zzemlyanaya.pulsepower.home.data.model.OrderResponse
import ru.zzemlyanaya.pulsepower.home.domain.model.MembershipEntity
import ru.zzemlyanaya.pulsepower.placeSelect.data.mapping.PlacesResponseToEntityMapper
import java.util.Locale
import javax.inject.Inject

class StoreResponseToEntityMapper @Inject constructor(
    private val placesMapper: PlacesResponseToEntityMapper
) {

    fun mapOrders(response: GetOrdersResponse) = response.orders.orEmpty().map(::mapMembership)

    fun mapMembership(data: OrderResponse): MembershipEntity {
        val duration = getDuration(data.subscription?.duration)

        val date = SimpleDateFormat(DATE_FORMAT_SERVER, Locale.getDefault()).parse(data.date)
        date.month += duration.months

        return MembershipEntity(
            id = data.id.orEmpty(),
            isActive = data.isExpired == false,
            expirationDate = SimpleDateFormat(DATE_FORMAT_SHORT, Locale.getDefault()).format(date),
            timeOfTheDay = getTimeOfTheDay(data.subscription?.timeOfDay),
            type = getType(data.subscription?.options),
            duration = duration
        )
    }

    private fun getTimeOfTheDay(time: String?) =
        try {
            TimeOfTheDay.valueOf(time.orEmpty())
        } catch (e: IllegalArgumentException) {
            TimeOfTheDay.AllDay
        }

    private fun getType(type: String?) =
        try {
            MembershipType.valueOf(type.orEmpty())
        } catch (e: IllegalArgumentException) {
            MembershipType.AllInclusive
        }

    private fun getDuration(duration: Int?) =
        when (duration) {
            3 -> MembershipDuration.MONTHS_3
            6 -> MembershipDuration.MONTHS_6
            else -> MembershipDuration.YEAR
        }

    companion object {
        const val DATE_FORMAT_SERVER = "yyyy-MM-dd'T'HH:mm:ss.SSSz"
        const val DATE_FORMAT_SHORT = "dd.MM.yyyy"
    }
}