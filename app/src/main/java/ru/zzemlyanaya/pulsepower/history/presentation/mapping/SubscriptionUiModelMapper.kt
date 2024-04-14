package ru.zzemlyanaya.pulsepower.history.presentation.mapping

import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.history.domain.model.*
import ru.zzemlyanaya.pulsepower.history.presentation.model.SubscriptionUiModel
import javax.inject.Inject

class SubscriptionUiModelMapper @Inject constructor() {

//    fun map(data: List<OrderEntity>) = data.map(::mapOrder)
//
//    private fun mapOrder(data: OrderEntity) = SubscriptionUiModel(
//        description = buildDescription(data.subscription),
//        period =
//    )
//
//    private fun buildDescription(subscription: SubscriptionEntity) =
//        if (subscription.type == SubscriptionType.AllInclusive) {
//            if (subscription.timeOfDay == TimeOfTheDay.AllDay) {
//                getString(R.string.description_type_2, subscription.type.text, subscription.timeOfDay.text)
//            } else {
//                getString(R.string.description_type_3, subscription.timeOfDay.text, subscription.type.text)
//            }
//        } else {
//            if (subscription.timeOfDay == TimeOfTheDay.AllDay) {
//                getString(R.string.description_type_3, subscription.timeOfDay.text, subscription.type.text)
//            } else {
//                getString(R.string.description_type_1, subscription.type.text, subscription.timeOfDay.text)
//            }
//        }
//
//    private fun buildPeriod(subscription: SubscriptionEntity) =

}