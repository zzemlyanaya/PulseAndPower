package ru.zzemlyanaya.pulsepower.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.*
import androidx.navigation.compose.navigation
import ru.zzemlyanaya.pulsepower.app.navigation.*
import ru.zzemlyanaya.pulsepower.app.theme.PulsePowerTheme
import ru.zzemlyanaya.pulsepower.auth.presentation.ui.*
import ru.zzemlyanaya.pulsepower.history.presentation.ui.HistoryScreen
import ru.zzemlyanaya.pulsepower.home.presentation.ui.HomeScreen
import ru.zzemlyanaya.pulsepower.placeSelect.presentation.ui.PlaceSelectScreen
import ru.zzemlyanaya.pulsepower.profile.presentation.ui.ProfileScreen
import ru.zzemlyanaya.pulsepower.profile.presentation.ui.UserInfoScreen

@Composable
fun PulseAndPowerApp(navigationRouter: NavigationRouter) {

    PulsePowerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val navController = rememberNavController()

            navigationRouter.commands.collectAsState().value.also { command ->
                if (command.destination == MainDirections.back.destination) {
                    navController.popBackStack()
                } else if (command.destination.isNotEmpty()) {
                    navController.navigate(command.destination)
                }
            }

            AppNavGraph(navController)
        }
    }
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = MainDestination.Auth.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        navigation(route = MainDestination.Auth.route, startDestination = AuthDestination.SignIn.route) {
            composable(AuthDestination.SignIn.route) {
                AuthScreen(modifier = modifier)
            }

            composable(AuthDestination.SignUp.route) {
                SignUpScreen(modifier = modifier)
            }

            composable(AuthDestination.PhoneConfirm.route) {
                PhoneConfirmScreen(modifier = modifier)
            }
        }

        navigation(route = MainDestination.Home.route, startDestination = HomeDestination.Home.route) {
            composable(HomeDestination.Home.route) {
                HomeScreen(modifier = modifier)
            }

            composable(HomeDestination.Profile.route) {
                ProfileScreen(modifier = modifier)
            }

            composable(HomeDestination.History.route) {
                HistoryScreen(modifier = modifier)
            }

            composable(HomeDestination.UserInfo.route) {
                UserInfoScreen(modifier = modifier)
            }
        }

        composable(MainDestination.PlaceSelect.route) {
            PlaceSelectScreen(modifier = modifier)
        }
    }
}