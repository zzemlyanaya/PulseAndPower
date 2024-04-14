package ru.zzemlyanaya.pulsepower.home.presentation.model

import androidx.annotation.StringRes
import ru.zzemlyanaya.pulsepower.history.domain.model.*
import java.io.Serializable

data class HomeUiState(
    val timeOfTheDayOption: List<OptionUiModel<TimeOfTheDay>> = listOf(),
    val typeOptions: List<OptionUiModel<MembershipType>> = listOf(),
    val durationOptions: List<OptionUiModel<MembershipDuration>> = listOf(),
    val updateFlag: Boolean = false
)

class OptionUiModel<T>(
    val option: T,
    @StringRes val name: Int,
    var isSelected: Boolean
) : Serializable