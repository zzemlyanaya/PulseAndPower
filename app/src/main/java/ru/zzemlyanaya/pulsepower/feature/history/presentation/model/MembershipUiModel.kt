package ru.zzemlyanaya.pulsepower.feature.history.presentation.model

import android.text.SpannableString
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString

data class MembershipUiModel(
    val id: String,
    val description: AnnotatedString,
    val pulseColor: Color,
    val isRepeatable: Boolean
)
