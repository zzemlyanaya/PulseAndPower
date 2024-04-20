package ru.zzemlyanaya.pulsepower.feature.auth.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.PulsePowerTheme
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.feature.auth.presentation.model.contract.SignUpContract
import ru.zzemlyanaya.pulsepower.feature.auth.presentation.viewmodel.SignUpViewModel
import ru.zzemlyanaya.pulsepower.core.ui.BaseScreen
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.ui.PlaceSelectInput
import ru.zzemlyanaya.pulsepower.uikit.*

@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<SignUpViewModel>()

    BaseScreen<SignUpContract.UiState>(
        modifier = modifier,
        uiFlow = viewModel.screenState,
        sendIntent = viewModel::sendIntent,
        errorContent = { mModifier, uiState, sendEvent ->
            SignUpScreen(
                mModifier,
                uiState,
                sendEvent,
                showError = true
            )
        },
        loadingContent = { mModifier, uiState, sendEvent ->
            SignUpScreen(
                mModifier,
                uiState,
                sendEvent,
                showLoading = true
            )
        },
        dataContent = { mModifier, uiState, sendEvent -> SignUpScreen(mModifier, uiState, sendEvent) }
    )

}

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    uiState: SignUpContract.UiState,
    sendIntent: (BaseIntent) -> Unit,
    showLoading: Boolean = false,
    showError: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Column {
            Logo(mode = LOGO_AT_START)
        }

        Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
            Text(
                text = stringResource(id = R.string.tell_us_about_yourself),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium
            )

            OutlinedTextInput(
                value = uiState.name,
                onValueChanged = { sendIntent(SignUpContract.Intent.UpdateName(it)) },
                placeholder = stringResource(id = R.string.name),
                error = uiState.nameError,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    autoCorrect = false
                ),
                keyboardActions = KeyboardActions(onNext = { })
            )

            OutlinedTextInput(
                value = uiState.surname,
                onValueChanged = { sendIntent(SignUpContract.Intent.UpdateSurname(it)) },
                placeholder = stringResource(id = R.string.surname),
                error = uiState.surnameError,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    autoCorrect = false
                ),
                keyboardActions = KeyboardActions(onNext = { })
            )

            OutlinedTextInput(
                value = uiState.patronymic,
                onValueChanged = { sendIntent(SignUpContract.Intent.UpdatePatronymic(it)) },
                placeholder = stringResource(id = R.string.patronymic),
                error = uiState.patronymicError,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    autoCorrect = false
                ),
                keyboardActions = KeyboardActions(onNext = { })
            )

            PlaceSelectInput(
                title = stringResource(id = R.string.favourite_places),
                text = uiState.favouritePlaces,
                onClick = { sendIntent(SignUpContract.Intent.SelectPlaces) }
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
                text = stringResource(id = R.string.ready),
                onClick = { sendIntent(SignUpContract.Intent.SignUp) }
            )
        }

        if (showError) DefaultErrorDialog()
    }
}

@Preview(device = Devices.PIXEL_4A, heightDp = 750)
@Composable
fun SignUpScreenPreview() {
    PulsePowerTheme {
        SignUpScreen(
            modifier = Modifier,
            uiState = SignUpContract.UiState(),
            sendIntent = {}
        )
    }
}