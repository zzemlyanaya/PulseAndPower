package ru.zzemlyanaya.pulsepower.app.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import ru.zzemlyanaya.pulsepower.app.navigation.MainDirections.back
import ru.zzemlyanaya.pulsepower.app.navigation.MainDirections.default

class NavigationRouter {

    var commands = MutableStateFlow(default)

    fun navigateTo(directions: NavigationCommand) {
        commands.value = directions
    }

    fun back() {
        commands.value = back
    }
}