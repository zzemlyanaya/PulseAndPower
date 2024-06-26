package ru.zzemlyanaya.pulsepower.feature.auth.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.PulsePowerTheme
import ru.zzemlyanaya.pulsepower.app.theme.blue_68a4ff_60
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.feature.auth.presentation.model.contract.CodeConfirmContract
import ru.zzemlyanaya.pulsepower.feature.auth.presentation.model.contract.CodeConfirmContract.Intent.*
import ru.zzemlyanaya.pulsepower.feature.auth.presentation.viewmodel.PhoneConfirmViewModel
import ru.zzemlyanaya.pulsepower.core.ui.BaseScreen
import ru.zzemlyanaya.pulsepower.uikit.*

@Composable
fun PhoneConfirmScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<PhoneConfirmViewModel>()

    BaseScreen<CodeConfirmContract.UiState>(
        modifier = modifier,
        uiFlow = viewModel.screenState,
        sendIntent = viewModel::sendIntent,
        errorContent = { mModifier, uiState, sendEvent ->
            PhoneConfirmScreen(
                mModifier,
                uiState,
                sendEvent,
                showError = true
            )
        },
        loadingContent = { mModifier, uiState, sendEvent ->
            PhoneConfirmScreen(
                mModifier,
                uiState,
                sendEvent,
                showLoading = true
            )
        },
        dataContent = { mModifier, uiState, sendEvent -> PhoneConfirmScreen(mModifier, uiState, sendEvent) }
    )
}

@Composable
fun PhoneConfirmScreen(
    modifier: Modifier = Modifier,
    uiState: CodeConfirmContract.UiState,
    sendIntent: (BaseIntent) -> Unit,
    showLoading: Boolean = false,
    showError: Boolean = false
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
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
            verticalArrangement = Arrangement.Center
        ) {
            PinView(
                pinText = uiState.code,
                onPinTextChange = { sendIntent(UpdateCode(it)) },
                error = uiState.codeError,
                digitCount = 4
            )
        }

        if (showLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            BottomButton(
                text = stringResource(id = R.string.enter_code),
                onClick = { sendIntent(EnterCode) }
            )
        }

        if (showError) DefaultErrorDialog()
    }
}

@Composable
fun PinView(
    pinText: String,
    onPinTextChange: (String) -> Unit,
    error: String? = null,
    digitCount: Int = 4
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            value = pinText,
            onValueChange = onPinTextChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    repeat(digitCount) { index ->
                        val digitText = if (index >= pinText.length) "" else pinText[index].toString()

                        DigitView(
                            digitText = digitText,
                            borderColor = if (error != null) {
                                MaterialTheme.colorScheme.error
                            } else {
                                if (digitText.isEmpty()) {
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                                } else MaterialTheme.colorScheme.primary
                            }
                        )
                    }
                }
            }
        )

        AnimatedVisibility(visible = error != null) {
            Text(
                text = error.orEmpty(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@Composable
private fun DigitView(
    digitText: String,
    borderColor: Color
) {
    Box(
        modifier = Modifier
            .size(width = 54.dp, height = 56.dp)
            .border(width = 2.dp, color = borderColor, shape = MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.surfaceContainer, shape = MaterialTheme.shapes.medium),
        contentAlignment = Alignment.Center

    ) {
        Text(
            text = digitText,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(device = Devices.PIXEL_4A, heightDp = 750)
@Composable
fun CodeConfirmScreenPreview() {
    PulsePowerTheme {
        PhoneConfirmScreen(
            modifier = Modifier.fillMaxSize(),
            uiState = CodeConfirmContract.UiState(),
            sendIntent = {}
        )
    }
}

@Preview
@Composable
fun DigitPreview() {
    PulsePowerTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            DigitView(digitText = "", borderColor = blue_68a4ff_60)
            DigitView(digitText = "1", borderColor = MaterialTheme.colorScheme.primary)
            DigitView(digitText = "1", borderColor = MaterialTheme.colorScheme.error)
        }
    }
}

@Preview
@Composable
fun PinPreview() {
    PulsePowerTheme {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            PinView(pinText = "", onPinTextChange = {}, error = null)
            PinView(pinText = "12", onPinTextChange = {}, error = null)
            PinView(pinText = "1234", onPinTextChange = {}, error = null)
            PinView(pinText = "0000", onPinTextChange = {}, error = "")
            PinView(pinText = "0000", onPinTextChange = {}, error = "Превышено допустимое количество попыток входа")
        }
    }
}