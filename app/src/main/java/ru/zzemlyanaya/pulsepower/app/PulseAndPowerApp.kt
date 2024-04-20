package ru.zzemlyanaya.pulsepower.app

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.*
import androidx.navigation.compose.*
import androidx.navigation.compose.navigation
import ru.zzemlyanaya.pulsepower.app.navigation.*
import ru.zzemlyanaya.pulsepower.app.theme.PulsePowerTheme
import ru.zzemlyanaya.pulsepower.feature.auth.presentation.ui.*
import ru.zzemlyanaya.pulsepower.feature.history.presentation.ui.HistoryScreen
import ru.zzemlyanaya.pulsepower.feature.home.presentation.ui.HomeScreen
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.ui.PlaceSelectScreen
import ru.zzemlyanaya.pulsepower.feature.profile.presentation.ui.ProfileScreen
import ru.zzemlyanaya.pulsepower.feature.profile.presentation.ui.UserInfoScreen

@Composable
fun PulseAndPowerApp(navigationRouter: NavigationRouter) {

    PulsePowerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val navController = rememberNavController()

            navigationRouter.commands.observe(LocalLifecycleOwner.current) { nextCommand ->
                if (nextCommand.destination == MainDirections.back.destination) {
                    navController.popBackStack()
                } else if (nextCommand.destination.isNotEmpty()) {
                    navController.navigate(nextCommand.destination) { launchSingleTop = true }
                }
            }

            AppNavGraph(navController, onBack = navigationRouter::back)
        }
    }
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    onBack: () -> Unit,
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
                BackHandler(true) {}
                SignUpScreen(modifier = modifier)
            }

            composable(AuthDestination.PhoneConfirm.route) {
                PhoneConfirmScreen(modifier = modifier)
            }
        }

        navigation(route = MainDestination.Home.route, startDestination = HomeDestination.Home.route) {
            composable(HomeDestination.Home.route) {
                BackHandler(true) {}
                HomeScreen(modifier = modifier)
            }

            composable(HomeDestination.Profile.route) {
                BackHandler(true, onBack)
                ProfileScreen(modifier = modifier)
            }

            composable(HomeDestination.History.route) {
                BackHandler(true, onBack)
                HistoryScreen(modifier = modifier)
            }

            composable(HomeDestination.UserInfo.route) {
                BackHandler(true, onBack)
                UserInfoScreen(modifier = modifier)
            }
        }

        composable(MainDestination.PlaceSelect.route) {
            BackHandler(true, onBack)
            PlaceSelectScreen(modifier = modifier)
        }
    }
}