package ru.zzemlyanaya.pulsepower.app.navigation


sealed class MainDestination(val route: String) {
    data object Auth : MainDestination("auth_route")
    data object Home : MainDestination("home_route")
    data object PlaceSelect : MainDestination("place_select")
}

sealed class AuthDestination(val route: String) {
    data object SignIn : AuthDestination("sign_in")
    data object SignUp : AuthDestination("sign_up")
    data object PhoneConfirm : AuthDestination("phone_confirm")
}

sealed class HomeDestination(val route: String) {
    data object Home : HomeDestination("home")
    data object Profile : HomeDestination("profile")
    data object UserInfo : HomeDestination("user_info")
    data object History : HomeDestination("history")
}