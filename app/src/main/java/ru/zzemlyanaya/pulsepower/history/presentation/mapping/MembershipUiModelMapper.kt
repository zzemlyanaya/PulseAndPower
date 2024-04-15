package ru.zzemlyanaya.pulsepower.history.presentation.mapping

import androidx.compose.ui.text.*
import androidx.compose.ui.text.intl.Locale
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.purple_9999ff
import ru.zzemlyanaya.pulsepower.app.theme.white
import ru.zzemlyanaya.pulsepower.core.utils.ResourceProvider
import ru.zzemlyanaya.pulsepower.history.domain.model.*
import ru.zzemlyanaya.pulsepower.history.presentation.model.MembershipUiModel
import ru.zzemlyanaya.pulsepower.home.domain.model.MembershipEntity
import javax.inject.Inject

class MembershipUiModelMapper @Inject constructor(
    private val resourceProvider: ResourceProvider
) {

    fun map(data: List<MembershipEntity>) = data.map(::mapMembership)

    fun mapMembership(data: MembershipEntity) = MembershipUiModel(
        id = data.id,
        description = buildDescription(data),
        pulseColor = if (data.isActive) purple_9999ff else white,
        isRepeatable = data.isActive.not()
    )

    private fun buildDescription(subscription: MembershipEntity): AnnotatedString {
        val res = if (subscription.type == MembershipType.AllInclusive) {
            if (subscription.timeOfTheDay == TimeOfTheDay.AllDay) {
                listOf(R.string.description_type_2,
                    subscription.type.text,
                    subscription.timeOfTheDay.text
                )
            } else {
               listOf(
                    R.string.description_type_3,
                    subscription.timeOfTheDay.text,
                    subscription.type.text
               )
            }
        } else {
            if (subscription.timeOfTheDay == TimeOfTheDay.AllDay) {
                listOf(
                    R.string.description_type_3,
                    subscription.type.text,
                    subscription.timeOfTheDay.text
                )
            } else {
                listOf(
                    R.string.description_type_1,
                    subscription.type.text,
                    subscription.timeOfTheDay.text
                )
            }
        }

        val arg1 = resourceProvider.getString(res[1])
        val arg2 = resourceProvider.getString(res[2])
        val words = arrayListOf(arg1, arg2)

        val base = StringBuilder(resourceProvider.getString(res[0], arg1, arg2))
        base.append("\n\n")

        resourceProvider.getString(subscription.duration.text).let {
            base.append(resourceProvider.getString(R.string.for_period, it))
            words.add(it)
        }

        if (subscription.isActive) {
            base.append(" " + resourceProvider.getString(R.string.until, subscription.expirationDate))
            words.add(subscription.expirationDate)
        }

        return getAnnotatedString(base.toString().capitalize(), words)
    }

    fun getAnnotatedString(text: String, words: List<String>): AnnotatedString = buildAnnotatedString {
        append(text)

        words.forEach { word ->
            if (text.contains(word)) {
                val offsetStart = text.indexOf(word)
                val offsetEnd = offsetStart + word.length

                addStyle(
                    style = SpanStyle(color = purple_9999ff),
                    start = offsetStart,
                    end = offsetEnd
                )
            }
        }
    }

}