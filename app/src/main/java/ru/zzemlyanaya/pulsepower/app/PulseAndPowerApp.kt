package ru.zzemlyanaya.pulsepower.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.*
import androidx.navigation.compose.navigation
import ru.zzemlyanaya.pulsepower.app.navigation.*
import ru.zzemlyanaya.pulsepower.app.theme.PulsePowerTheme
import ru.zzemlyanaya.pulsepower.auth.presentation.ui.*
import ru.zzemlyanaya.pulsepower.auth.presentation.viewmodel.*
import ru.zzemlyanaya.pulsepower.history.presentation.ui.HistoryScreen
import ru.zzemlyanaya.pulsepower.history.presentation.viewModel.HistoryViewModel
import ru.zzemlyanaya.pulsepower.home.presentation.ui.HomeScreen
import ru.zzemlyanaya.pulsepower.home.presentation.viewModel.HomeViewModel
import ru.zzemlyanaya.pulsepower.placeSelect.presentation.ui.PlaceSelectScreen
import ru.zzemlyanaya.pulsepower.placeSelect.presentation.viewModel.PlaceSelectViewModel
import ru.zzemlyanaya.pulsepower.profile.presentation.ui.ProfileScreen

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
                val viewModel = hiltViewModel<AuthViewModel>()
                AuthScreen(modifier = modifier, viewModel = viewModel)
            }

            composable(AuthDestination.SignUp.route) {
                val viewModel = hiltViewModel<SignUpViewModel>()
                SignUpScreen(modifier = modifier, viewModel = viewModel)
            }

            composable(AuthDestination.PhoneConfirm.route) {
                val viewModel = hiltViewModel<PhoneConfirmViewModel>()
                PhoneConfirmScreen(modifier = modifier, viewModel = viewModel)
            }
        }

        navigation(route = MainDestination.Home.route, startDestination = HomeDestination.Home.route) {
            composable(HomeDestination.Home.route) {
                val viewModel = hiltViewModel<HomeViewModel>()
                HomeScreen(modifier = modifier, viewModel = viewModel)
            }

            composable(HomeDestination.Profile.route) {
//                val viewModel = hiltViewModel<ProfileViewModel>()
                ProfileScreen(modifier = modifier)
            }

            composable(HomeDestination.History.route) {
                val viewModel = hiltViewModel<HistoryViewModel>()
                HistoryScreen(modifier = modifier, viewModel = viewModel)
            }
        }

        composable(MainDestination.PlaceSelect.route) {
            val viewModel = hiltViewModel<PlaceSelectViewModel>()
            PlaceSelectScreen(modifier = modifier, viewModel = viewModel)
        }
    }
}