package ru.zzemlyanaya.pulsepower.app.navigation

import java.io.Serializable

abstract class NavigationCommand {
    open val args: List<Serializable> = emptyList()
    abstract val destination: String

//    override fun equals(other: Any?) = false
}