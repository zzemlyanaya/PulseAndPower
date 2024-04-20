package ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.*
import ru.zzemlyanaya.pulsepower.core.ui.BaseScreen
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.model.CityItemUiModel
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.model.PlaceUiModel
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.model.contract.PlaceSelectContract
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.viewModel.PlaceSelectViewModel
import ru.zzemlyanaya.pulsepower.uikit.BottomButton
import ru.zzemlyanaya.pulsepower.uikit.Toolbar

@Composable
fun PlaceSelectScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<PlaceSelectViewModel>()

    BaseScreen<PlaceSelectContract.UiState>(
        modifier = modifier,
        uiFlow = viewModel.screenState,
        sendIntent = viewModel::sendIntent,
        loadingContent = { mModifier, uiState, sendIntent -> PlaceSelectScreen(mModifier, uiState, sendIntent, true) },
        dataContent = { mModifier, uiState, sendIntent -> PlaceSelectScreen(mModifier, uiState, sendIntent) }
    )
}

@Composable
fun PlaceSelectScreen(
    modifier: Modifier = Modifier,
    uiState: PlaceSelectContract.UiState,
    sendIntent: (BaseIntent) -> Unit,
    showLoading: Boolean = false
) {
    if (showLoading) {
        Box(modifier = Modifier.fillMaxSize().background(mate_black_container)) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Toolbar(title = stringResource(id = uiState.title), onBackPressed = { sendIntent(BaseIntent.Back) })

            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = stringResource(id = R.string.choose_up_to_5),
                style = MaterialTheme.typography.bodySmall,
                color = if (uiState.isError) MaterialTheme.colorScheme.error else white_caption
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                uiState.cities.forEach { city ->
                    CityItem(
                        modifier = Modifier.fillMaxWidth(),
                        city = city,
                        hasSelected = city.hasSelected,
                        isOpen = city.isOpen,
                        onCityClick = { sendIntent(PlaceSelectContract.Intent.CityClick(it)) },
                        onPlaceClick = { sendIntent(PlaceSelectContract.Intent.PlaceClick(city.name, it)) }
                    )
                }
            }
        }

        BottomButton(
            text = stringResource(id = R.string.ready),
            onClick = { sendIntent(BaseIntent.Back) }
        )
    }
}

@Composable
fun CityItem(
    modifier: Modifier = Modifier,
    city: CityItemUiModel,
    hasSelected: Boolean,
    isOpen: Boolean,
    onCityClick: (CityItemUiModel) -> Unit,
    onPlaceClick: (String) -> Unit
) {
    val rotation = animateFloatAsState(targetValue = if (isOpen) 90f else 0f, label = "arrow animation")

    Column(modifier) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onCityClick(city) }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
        ) {
            Text(
                text = city.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = if (hasSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )

            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = "Arrow right",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(40.dp)
                    .graphicsLayer(rotationZ = rotation.value)
            )
        }

        AnimatedVisibility(visible = isOpen) {
            Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                city.places.forEach { place ->
                    PlaceItem(
                        modifier = modifier,
                        place = place,
                        isSelected = place.isSelected,
                        onClick = { onPlaceClick(place.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun PlaceItem(
    modifier: Modifier = Modifier,
    place: PlaceUiModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        text = place.name,
        style = MaterialTheme.typography.bodyMedium,
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun PlaceSelectInput(
    modifier: Modifier = Modifier,
    text: String,
    title: String? = null,
    error: String? = null,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)) {
            title?.let {
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = title,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Icon(
                    imageVector = Icons.Rounded.LocationOn,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Location pin"
                )

                Text(
                    modifier = Modifier.padding(start = 6.dp),
                    text = error ?: text.takeUnless { it.isBlank() } ?: stringResource(id = R.string.place_select_prompt),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Start,
                    maxLines = 2
                )
            }
        }
    }
}

@Preview
@Composable
fun PlaceItemPreview() {
    PulsePowerTheme {
        var isSelected by remember { mutableStateOf(false) }

        PlaceItem(
            place = PlaceUiModel("id1", "Ад, Power&Pulse Центр", false),
            isSelected = isSelected,
            onClick = { isSelected = !isSelected }
        )
    }
}

@Preview(widthDp = 300)
@Composable
fun CityItemPreview() {
    PulsePowerTheme {
        var isOpen by remember { mutableStateOf(false) }

        CityItem(
            city = CityItemUiModel(
                "Ад",
                listOf(
                    PlaceUiModel("x1", "Power&Pulse Центр", false),
                    PlaceUiModel("x2", "Power&Pulse Дворцовая площадь", false),
                    PlaceUiModel("x3", "Power&Pulse Котлы", false)
                ),
                false
            ),
            hasSelected = false,
            isOpen = isOpen,
            onCityClick = { isOpen = !isOpen },
            onPlaceClick = { }
        )
    }
}

@Preview
@Composable
fun PlaceSelectInputPreview() {
    PulsePowerTheme {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            PlaceSelectInput(
                title = "Любимые места:",
                text = "Ад, Power&Pulse Центр и ещё 1 филиал asufdhsfugasdufgasufsDUFHidus",
                onClick = {}
            )

            PlaceSelectInput(
                text = "Ад, Power&Pulse Центр",
                onClick = {}
            )

            PlaceSelectInput(
                text = "",
                onClick = {}
            )

            PlaceSelectInput(
                text = "Ад, Power&Pulse Центр",
                error = "Выберите хотя бы один филиал",
                onClick = {}
            )
        }
    }
}

@Preview(device = Devices.PIXEL_4A, heightDp = 750)
@Composable
fun PlaceSelectScreenPreview() {
    PulsePowerTheme {
        PlaceSelectScreen(Modifier.fillMaxSize(), PlaceSelectContract.UiState(), {})
    }
}

@Preview(device = Devices.PIXEL_4A, heightDp = 750)
@Composable
fun PlaceSelectScreenLoadingPreview() {
    PulsePowerTheme {
        PlaceSelectScreen(Modifier.fillMaxSize(), PlaceSelectContract.UiState(), {}, true)
    }
}