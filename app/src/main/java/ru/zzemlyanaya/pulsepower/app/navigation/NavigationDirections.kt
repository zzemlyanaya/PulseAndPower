package ru.zzemlyanaya.pulsepower.app.navigation

import androidx.navigation.NamedNavArgument

object MainDirections {

    val default = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = ""
    }

    val back = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "back"
    }

    val auth  = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = MainDestination.Auth.route
    }

    val home = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = MainDestination.Home.route
    }

    val placeSelect = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = MainDestination.PlaceSelect.route
    }
}

object AuthDirections {

    val signIn = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = AuthDestination.SignIn.route
    }

    val signUp = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = AuthDestination.SignUp.route
    }

    val phoneConfirm = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = AuthDestination.PhoneConfirm.route
    }
}

object HomeDirections {

    val home = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = HomeDestination.Home.route
    }

    val profile = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = HomeDestination.Profile.route
    }

    val history = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = HomeDestination.History.route
    }
}