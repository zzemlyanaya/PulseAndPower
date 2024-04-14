package ru.zzemlyanaya.pulsepower.history.domain.model

import androidx.annotation.StringRes
import ru.zzemlyanaya.pulsepower.R

enum class TimeOfTheDay(@StringRes val text: Int) {
    Morning(R.string.morning), Evening(R.string.evening), AllDay(R.string.all_day)
}

enum class MembershipType(@StringRes val text: Int) {
    Single(R.string.individual), Group(R.string.group), AllInclusive(R.string.all_inclusive)
}

enum class MembershipDuration(@StringRes val text: Int, val months: Int) {
    MONTHS_3(R.string.months3, 3), MONTHS_6(R.string.months6, 6), YEAR(R.string.year1, 12)
}