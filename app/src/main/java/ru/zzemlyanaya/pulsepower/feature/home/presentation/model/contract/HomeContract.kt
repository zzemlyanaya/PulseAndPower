package ru.zzemlyanaya.pulsepower.feature.home.presentation.model.contract

import androidx.compose.ui.text.AnnotatedString
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.feature.history.domain.model.*
import ru.zzemlyanaya.pulsepower.feature.home.presentation.model.OptionUiModel
import ru.zzemlyanaya.pulsepower.feature.home.presentation.model.PaymentState

sealed interface HomeContract {

    data class UiState(
        val userName: String = "",
        val currentMembership: AnnotatedString = AnnotatedString(""),
        val price: String = "",
        val favouritePlaces: String = "",
        val paymentState: PaymentState = PaymentState.NONE,
        val timeOfTheDayOption: List<OptionUiModel<TimeOfTheDay>> = listOf(),
        val typeOptions: List<OptionUiModel<MembershipType>> = listOf(),
        val durationOptions: List<OptionUiModel<MembershipDuration>> = listOf(),
    )

    sealed interface Intent : BaseIntent {
        data class SelectTimeOfTheDay(val timeOfTheDay: TimeOfTheDay) : Intent
        data class SelectType(val membershipType: MembershipType) : Intent
        data class SelectDuration(val membershipDuration: MembershipDuration) : Intent

        data object OpenProfile : Intent
        data object OpenHistory : Intent
        data object SelectPlace : Intent
        data object Pay : Intent
        data object ClosePaymentDialog : Intent
    }
}