package ru.zzemlyanaya.pulsepower.feature.home.presentation.model

import androidx.annotation.StringRes
import java.io.Serializable

class OptionUiModel<T>(
    val option: T,
    @StringRes val name: Int,
    var isSelected: Boolean
) : Serializable