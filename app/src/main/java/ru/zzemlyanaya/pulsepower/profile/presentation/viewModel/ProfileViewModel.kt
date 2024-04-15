package ru.zzemlyanaya.pulsepower.profile.presentation.viewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.navigation.HomeDirections
import ru.zzemlyanaya.pulsepower.app.navigation.NavigationRouter
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.core.utils.ResourceProvider
import ru.zzemlyanaya.pulsepower.core.viewModel.BaseViewModel
import ru.zzemlyanaya.pulsepower.profile.presentation.model.contract.ProfileContract
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val router: NavigationRouter
) : BaseViewModel<ProfileContract.UiState, ProfileContract.Intent>(router) {

    override fun getInitialState() = ProfileContract.UiState

    override fun handleIntent(intent: BaseIntent) {
        when (intent) {
            is ProfileContract.Intent.OpenMyInfo -> router.navigateTo(HomeDirections.userInfo)
            is ProfileContract.Intent.OpenHistory -> router.navigateTo(HomeDirections.history)
            else -> super.handleIntent(intent)
        }
    }
}