package ru.zzemlyanaya.pulsepower.auth.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.PulsePowerTheme
import ru.zzemlyanaya.pulsepower.auth.presentation.model.intent.AuthIntent
import ru.zzemlyanaya.pulsepower.auth.presentation.viewmodel.AuthViewModel
import ru.zzemlyanaya.pulsepower.uikit.*


@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel()
) {

    @Composable
    fun sendIntent(intent: AuthIntent) {
        LocalLifecycleOwner.current.lifecycleScope.launch { viewModel.sendIntent(intent) }
    }

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
                modifier = modifier,
                value = viewModel.authUiState.value.phone,
                onValueChanged = { scope.sendIntent(AuthIntent.UpdatePhone(it)) },
                error = viewModel.authUiState.value.phoneError,
                onDone = { scope.sendIntent(AuthIntent.SignInClick) }
            )
            Text(
                modifier = modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.sign_in_up_hint),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall,
            )
        }

        BottomButton(
            text = stringResource(id = R.string.login),
            onClick = viewModel::onSingInClick
        )
    }
}

@Preview(device = Devices.PIXEL_4A, heightDp = 750)
@Composable
fun AuthScreenPreview() {
    PulsePowerTheme {
        AuthScreen()
    }
}
