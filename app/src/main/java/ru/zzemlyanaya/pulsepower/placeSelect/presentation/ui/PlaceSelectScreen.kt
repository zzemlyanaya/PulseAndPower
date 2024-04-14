package ru.zzemlyanaya.pulsepower.placeSelect.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.*
import ru.zzemlyanaya.pulsepower.placeSelect.model.*
import ru.zzemlyanaya.pulsepower.placeSelect.presentation.viewModel.PlaceSelectViewModel
import ru.zzemlyanaya.pulsepower.uikit.BottomButton
import ru.zzemlyanaya.pulsepower.uikit.Toolbar

@Composable
fun PlaceSelectScreen(
    modifier: Modifier = Modifier,
    viewModel: PlaceSelectViewModel = viewModel()
) {
    PlaceSelectScreen(
        modifier = modifier,
        title = viewModel.placesUiState.value.title,
        isError = viewModel.placesUiState.value.isError,
        cities = viewModel.placesUiState.value.cities,
        onCityClick = viewModel::onCityClick,
        onPlaceClick = viewModel::onPlaceClick,
        onBack = viewModel::back
    )
}

@Composable
fun PlaceSelectScreen(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    isError: Boolean,
    cities: List<CityItemUiModel>,
    onCityClick: (CityItemUiModel) -> Unit,
    onPlaceClick: (String, String) -> Unit,
    onBack: () -> Unit
) {
    Surface {
        Column(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Toolbar(title = stringResource(id = title), onBackPressed = onBack)

                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = stringResource(id = R.string.choose_up_to_5),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isError) MaterialTheme.colorScheme.error else white_caption
                )

                Column(modifier = Modifier.fillMaxWidth()) {
                    cities.forEach { city ->
                        CityItem(
                            modifier = modifier.fillMaxWidth(),
                            city = city,
                            hasSelected = city.hasSelected,
                            isOpen = city.isOpen,
                            onCityClick = onCityClick,
                            onPlaceClick = onPlaceClick
                        )
                    }
                }
            }

            BottomButton(
                text = stringResource(id = R.string.ready),
                onClick = onBack
            )
        }
    }
}

@Composable
fun CityItem(
    modifier: Modifier = Modifier,
    city: CityItemUiModel,
    hasSelected: Boolean,
    isOpen: Boolean,
    onCityClick: (CityItemUiModel) -> Unit,
    onPlaceClick: (String, String) -> Unit
) {
    val rotation = animateFloatAsState(targetValue = if (isOpen) 90f else 0f, label = "arrow animation")

    Surface(modifier = modifier) {
        Column {
            Row(
                modifier = modifier.fillMaxWidth().clickable { onCityClick(city) }.padding(vertical = 12.dp),
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
                    modifier = Modifier.size(40.dp).graphicsLayer(rotationZ = rotation.value)
                )
            }

            AnimatedVisibility(visible = isOpen) {
                Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    city.places.forEach { place ->
                        PlaceItem(
                            modifier = modifier,
                            place = place,
                            cityName = city.name,
                            isSelected = place.isSelected,
                            onClick = onPlaceClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceItem(
    modifier: Modifier = Modifier,
    cityName: String,
    place: PlaceUiModel,
    isSelected: Boolean,
    onClick: (String, String) -> Unit
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .clickable {  onClick(cityName, place.id) }
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
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {

                Icon(
                    imageVector = Icons.Rounded.LocationOn,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Location pin"
                )

                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = error ?: text,
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
            cityName = "Ад",
            place = PlaceUiModel("id1", "Ад, Power&Pulse Центр", false),
            isSelected = isSelected,
            onClick = { _, _ -> isSelected = !isSelected }
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
                        PlaceUiModel("x2", "Power&Pulse Дворцовая площадь", false ),
                        PlaceUiModel("x3", "Power&Pulse Котлы", false)
                    ),
                false
            ),
            hasSelected = false,
            isOpen = isOpen,
            onCityClick = { isOpen = !isOpen },
            onPlaceClick = { _, _ -> }
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
                text = "Ад, Power&Pulse Центр и ещё 1 филиал",
                onClick = {}
            )

            PlaceSelectInput(
                text = "Ад, Power&Pulse Центр",
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
        PlaceSelectScreen(
            title = R.string.places_select,
            isError = false,
            cities = PlaceSelectViewModel.getData(),
            onCityClick = {},
            onPlaceClick = {_, _ -> },
            onBack = {}
        )
    }
}