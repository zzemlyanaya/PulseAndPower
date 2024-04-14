package ru.zzemlyanaya.pulsepower.core.utils

import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes resourceId: Int, varargs: Any): String

    fun getPluralString(@StringRes resourceId: Int, quantity: Int, varargs: Any): String
}