package ru.zzemlyanaya.pulsepower.history.presentation.model

import androidx.annotation.ColorRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString

data class SubscriptionUiModel(
    val description: AnnotatedString,
    val period: AnnotatedString,
    val pulseColor: Color,
    val isRepeatable: Boolean,
    @Transient val onRepeat: () -> Unit
)
