package ru.zzemlyanaya.pulsepower.auth.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.PulsePowerTheme
import ru.zzemlyanaya.pulsepower.auth.presentation.viewmodel.SignUpViewModel
import ru.zzemlyanaya.pulsepower.placeSelect.presentation.ui.PlaceSelectInput
import ru.zzemlyanaya.pulsepower.uikit.*

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = viewModel()
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
                value = viewModel.signUpUiState.value.name,
                onValueChanged = { viewModel.updateName(it) },
                placeholder = stringResource(id = R.string.name),
                error = viewModel.signUpUiState.value.nameError,
                modifier = modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next,
                    autoCorrect = false
                ),
                keyboardActions = KeyboardActions(onNext = { })
            )

            OutlinedTextInput(
                value = viewModel.signUpUiState.value.surname,
                onValueChanged = { viewModel.updateSurname(it) },
                placeholder = stringResource(id = R.string.surname),
                error = viewModel.signUpUiState.value.surnameError,
                modifier = modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next,
                    autoCorrect = false
                ),
                keyboardActions = KeyboardActions(onNext = { })
            )

            OutlinedTextInput(
                value = viewModel.signUpUiState.value.patronymic,
                onValueChanged = { viewModel.updatePatronymic(it) },
                placeholder = stringResource(id = R.string.patronymic),
                error = viewModel.signUpUiState.value.patronymicError,
                modifier = modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next,
                    autoCorrect = false
                ),
                keyboardActions = KeyboardActions(onNext = { })
            )

            PlaceSelectInput(
                title = stringResource(id = R.string.favourite_places),
                text = viewModel.signUpUiState.value.favouritePlaces,
                onClick = viewModel::onSelectPlaces
            )
        }

        BottomButton(
            text = stringResource(id = R.string.ready),
            onClick = viewModel::onSignUpClick
        )
    }
}

@Preview(device = Devices.PIXEL_4A, heightDp = 750)
@Composable
fun SignUpScreenPreview() {
    PulsePowerTheme {
        SignUpScreen()
    }
}