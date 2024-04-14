package ru.zzemlyanaya.pulsepower.history.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.PulsePowerTheme
import ru.zzemlyanaya.pulsepower.history.presentation.model.SubscriptionUiModel
import ru.zzemlyanaya.pulsepower.history.presentation.viewModel.HistoryViewModel
import ru.zzemlyanaya.pulsepower.uikit.*

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = viewModel()
) {
    val isEmpty = viewModel.historyUiState.value.isEmpty

        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top)) {
            Toolbar(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp),
                title = stringResource(id = R.string.history_title),
                onBackPressed = viewModel::back
            )

            if (isEmpty) EmptyHistoryScreen()
            else HistoryWithDataScreen(data = viewModel.historyUiState.value.items)
        }
}

@Composable
fun HistoryWithDataScreen(
    modifier: Modifier = Modifier,
    data: List<SubscriptionUiModel>
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 4.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(data) { item ->
            MembershipCard(
                pulseColor = item.pulseColor,
                description = item.description,
                period = item.period,
                isRepeatable = item.isRepeatable,
                onRepeat = item.onRepeat
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

@Preview(device = Devices.PIXEL_4A, heightDp = 750)
@Composable
fun HistoryScreenPreview() {
    PulsePowerTheme {
        HistoryWithDataScreen(data = HistoryViewModel.getData())
    }
}

@Preview(device = Devices.PIXEL_4A, heightDp = 750)
@Composable
fun HistoryEmptyScreenPreview() {
    PulsePowerTheme {
        EmptyHistoryScreen()
    }
}