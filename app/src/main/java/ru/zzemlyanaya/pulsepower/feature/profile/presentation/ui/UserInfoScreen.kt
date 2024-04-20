package ru.zzemlyanaya.pulsepower.feature.profile.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.zzemlyanaya.pulsepower.BuildConfig
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.feature.auth.presentation.model.contract.SignUpContract
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.core.ui.BaseScreen
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.ui.PlaceSelectInput
import ru.zzemlyanaya.pulsepower.feature.profile.presentation.model.contract.ProfileContract
import ru.zzemlyanaya.pulsepower.feature.profile.presentation.model.contract.UserInfoContract
import ru.zzemlyanaya.pulsepower.feature.profile.presentation.viewModel.UserInfoViewModel
import ru.zzemlyanaya.pulsepower.uikit.*

@Composable
fun UserInfoScreen(modifier: Modifier) {
    val viewModel = hiltViewModel<UserInfoViewModel>()

    BaseScreen<UserInfoContract.UiState>(
        modifier = modifier,
        uiFlow = viewModel.screenState,
        sendIntent = viewModel::sendIntent,
        loadingContent = { mModifier, uiState, sendIntent ->
            UserInfoScreen(
                modifier = mModifier,
                uiState = uiState,
                sendIntent = sendIntent,
                true
            )
        },
        dataContent = { mModifier, uiState, sendIntent ->
            UserInfoScreen(
                modifier = mModifier,
                uiState = uiState,
                sendIntent = sendIntent
            )
        }
    )
}

@Composable
fun UserInfoScreen(
    modifier: Modifier,
    uiState: UserInfoContract.UiState,
    sendIntent: (BaseIntent) -> Unit,
    showLoading: Boolean = false
) {
    Column(
        modifier = modifier.padding(start = 16.dp, top = 24.dp, bottom = 20.dp, end = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Toolbar(title = stringResource(id = R.string.my_info), onBackPressed = { sendIntent(BaseIntent.Back) })

        Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
            Text(
                text = stringResource(id = R.string.tell_us_about_yourself),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium
            )

            OutlinedTextInput(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.name,
                onValueChanged = {},
                placeholder = stringResource(id = R.string.name),
                enabled = false
            )

            OutlinedTextInput(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.surname,
                onValueChanged = { },
                placeholder = stringResource(id = R.string.surname),
                enabled = false
            )

            OutlinedTextInput(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.patronymic,
                onValueChanged = { },
                placeholder = stringResource(id = R.string.patronymic),
                enabled = false
            )

            OutlinedTextInput(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.phone,
                onValueChanged = { },
                placeholder = stringResource(id = R.string.phone),
                enabled = false
            )

            PlaceSelectInput(
                title = stringResource(id = R.string.favourite_places),
                text = uiState.favouritePlaces,
                onClick = { sendIntent(UserInfoContract.Intent.ChangeFavouritePlaces) }
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
                text = stringResource(id = R.string.save),
                onClick = { sendIntent(UserInfoContract.Intent.SaveChanges) }
            )
        }

    }
}

@Preview(device = Devices.PIXEL_4A, heightDp = 750)
@Composable
fun UserInfoScreenPreview() {
    UserInfoScreen(
        modifier = Modifier,
        uiState = UserInfoContract.UiState(),
        sendIntent = {}
    )
}