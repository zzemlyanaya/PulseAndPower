package ru.zzemlyanaya.pulsepower.feature.history.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.PulsePowerTheme
import ru.zzemlyanaya.pulsepower.core.ui.BaseScreen
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.feature.history.presentation.model.MembershipUiModel
import ru.zzemlyanaya.pulsepower.feature.history.presentation.model.contract.HistoryContract
import ru.zzemlyanaya.pulsepower.feature.history.presentation.viewModel.HistoryViewModel
import ru.zzemlyanaya.pulsepower.uikit.*

@Composable
fun HistoryScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<HistoryViewModel>()

    BaseScreen<HistoryContract.UiState>(
        modifier = modifier,
        uiFlow = viewModel.screenState,
        sendIntent = viewModel::sendIntent,
        loadingContent = { _, _, _ -> LoadingHistoryScreen() },
        dataContent = { mModifier, uiState, sendIntent -> HistoryScreen(mModifier, uiState, sendIntent) }
    )

}

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    uiState: HistoryContract.UiState,
    sendIntent: (BaseIntent) -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top)) {
        Toolbar(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp),
            title = stringResource(id = R.string.history_title),
            onBackPressed = { sendIntent(BaseIntent.Back) }
        )

        if (uiState.isEmpty) EmptyHistoryScreen()
        else HistoryWithDataScreen(modifier, uiState.items, sendIntent)
    }
}

@Composable
fun HistoryWithDataScreen(
    modifier: Modifier = Modifier,
    data: List<MembershipUiModel>,
    sendIntent: (BaseIntent) -> Unit
) {
    LazyColumn(modifier = modifier.padding(horizontal = 4.dp)) {
        items(data) { item ->
            MembershipCard(
                pulseColor = item.pulseColor,
                description = item.description,
                isRepeatable = item.isRepeatable,
                onRepeat = { sendIntent(HistoryContract.Intent.RepeatSubscription(item.id)) }
            )
        }
    }
}

@Composable
fun EmptyHistoryScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
    }
}

@Composable
fun LoadingHistoryScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(device = Devices.PIXEL_4A, heightDp = 750)
@Composable
fun HistoryScreenPreview() {
    PulsePowerTheme {
        HistoryScreen(Modifier.fillMaxSize(), HistoryContract.UiState(), { })
    }
}

@Preview(device = Devices.PIXEL_4A, heightDp = 750)
@Composable
fun HistoryEmptyScreenPreview() {
    PulsePowerTheme {
        EmptyHistoryScreen()
    }
}