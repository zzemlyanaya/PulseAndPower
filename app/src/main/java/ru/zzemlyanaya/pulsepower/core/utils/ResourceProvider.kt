package ru.zzemlyanaya.pulsepower.core.utils

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceProvider @Inject constructor(@ApplicationContext private val context: Context) {

    fun getString(@StringRes resId: Int): String = context.getString(resId)
    fun getString(@StringRes resId: Int, vararg arg: Any): String = context.getString(resId, *arg)

    fun getPlurals(@PluralsRes resId: Int, quantity: Int, vararg arg: Any): String =
        context.resources.getQuantityString(resId, quantity, *arg)
}