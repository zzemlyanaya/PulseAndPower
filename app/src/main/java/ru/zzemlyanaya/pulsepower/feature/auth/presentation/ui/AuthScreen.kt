package ru.zzemlyanaya.pulsepower.feature.auth.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.*
import ru.zzemlyanaya.pulsepower.feature.auth.presentation.model.contract.AuthContract
import ru.zzemlyanaya.pulsepower.feature.auth.presentation.model.contract.AuthContract.Intent.*
import ru.zzemlyanaya.pulsepower.feature.auth.presentation.viewmodel.AuthViewModel
import ru.zzemlyanaya.pulsepower.core.ui.BaseScreen
import ru.zzemlyanaya.pulsepower.uikit.*


@Composable
fun AuthScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<AuthViewModel>()

    BaseScreen<AuthContract.UiState>(
        modifier = modifier,
        uiFlow = viewModel.screenState,
        sendIntent = viewModel::sendIntent,
        loadingContent = { mModifier, uiState, sendEvent -> AuthScreen(mModifier, uiState, sendEvent, true) },
        dataContent = { mModifier, uiState, sendEvent -> AuthScreen(mModifier, uiState, sendEvent) }
    )
}

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    uiState: AuthContract.UiState,
    sendIntent: (AuthContract.Intent) -> Unit,
    showLoading: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(0.25f),
            verticalArrangement = Arrangement.Center
        ) {
            Logo()
        }

        Column(
            modifier = Modifier.fillMaxHeight(0.5f),
            verticalArrangement = Arrangement.Top
        ) {
            PhoneInput(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.phone,
                onValueChanged = { sendIntent(UpdatePhone(it)) },
                error = uiState.phoneError,
                onDone = { sendIntent(SignIn) }
            )
            Text(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.sign_in_up_hint),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall,
            )
        }

        if (showLoading) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            BottomButton(
                text = stringResource(id = R.string.login),
                onClick = { sendIntent(SignIn) }
            )
        }
    }
}

@Preview(device = Devices.PIXEL_4A, heightDp = 750)
@Composable
fun AuthScreenPreview() {
    PulsePowerTheme {
        AuthScreen(
            modifier = Modifier.fillMaxSize(),
            uiState = AuthContract.UiState(),
            sendIntent = { }
        )
    }
}

@Preview(device = Devices.PIXEL_4A, heightDp = 750)
@Composable
fun AuthScreenLoadingPreview() {
    PulsePowerTheme {
        AuthScreen(
            modifier = Modifier.fillMaxSize(),
            uiState = AuthContract.UiState(),
            sendIntent = { },
            showLoading = true
        )
    }
}
