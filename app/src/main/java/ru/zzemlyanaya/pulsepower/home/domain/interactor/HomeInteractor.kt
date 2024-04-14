package ru.zzemlyanaya.pulsepower.home.domain.interactor

import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.history.domain.model.*
import ru.zzemlyanaya.pulsepower.home.presentation.model.OptionUiModel
import javax.inject.Inject

class HomeInteractor @Inject constructor() {

    fun getTimeOptions() = listOf(
        OptionUiModel(TimeOfTheDay.Morning, R.string.morning, false),
        OptionUiModel(TimeOfTheDay.AllDay, R.string.all_day, true),
        OptionUiModel(TimeOfTheDay.Evening, R.string.evening, false)
    )

    fun getTypeOptions() = listOf(
        OptionUiModel(MembershipType.Single, R.string.individual, false),
        OptionUiModel(MembershipType.AllInclusive, R.string.all_inclusive_type, true),
        OptionUiModel(MembershipType.Group, R.string.group, false)
    )

    fun getDurationOptions() = listOf(
        OptionUiModel(MembershipDuration.MONTHS_3, R.string.months3, false),
        OptionUiModel(MembershipDuration.MONTHS_6, R.string.months6, true),
        OptionUiModel(MembershipDuration.YEAR, R.string.year1, false)
    )
}