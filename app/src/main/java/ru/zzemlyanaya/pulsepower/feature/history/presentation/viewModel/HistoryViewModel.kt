package ru.zzemlyanaya.pulsepower.feature.history.presentation.viewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.zzemlyanaya.pulsepower.app.navigation.NavigationRouter
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.core.viewModel.BaseViewModel
import ru.zzemlyanaya.pulsepower.feature.history.presentation.mapping.MembershipUiModelMapper
import ru.zzemlyanaya.pulsepower.feature.history.presentation.model.contract.HistoryContract
import ru.zzemlyanaya.pulsepower.feature.home.domain.interactor.StoreInteractor
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val interactor: StoreInteractor,
    private val mapper: MembershipUiModelMapper,
    private val router: NavigationRouter
) : BaseViewModel<HistoryContract.UiState, HistoryContract.Intent>(router) {

    override fun getInitialState() = HistoryContract.UiState()

    init {
        ioScope.launch {
            showLoading()
            val result = interactor.getAllMemberships()
            updateAndSetDataState { it.copy(items = mapper.map(result)) }
        }
    }

    override fun handleIntent(intent: BaseIntent) {
        if (intent is HistoryContract.Intent.RepeatSubscription) repeatSubscription(intent.id)
        super.handleIntent(intent)
    }

    private fun repeatSubscription(id: String) {
        val item = getUiState().items.first { it.id == id }
        // TODO repeat
    }
}