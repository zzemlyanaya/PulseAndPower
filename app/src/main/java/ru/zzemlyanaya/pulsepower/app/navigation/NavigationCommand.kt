package ru.zzemlyanaya.pulsepower.app.navigation

import java.io.Serializable

abstract class NavigationCommand {
    open val args: List<Any> = emptyList()
    abstract val destination: String
}