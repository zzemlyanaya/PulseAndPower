package ru.zzemlyanaya.pulsepower.home.presentation.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.*
import ru.zzemlyanaya.pulsepower.history.domain.model.*
import ru.zzemlyanaya.pulsepower.home.presentation.model.OptionUiModel
import ru.zzemlyanaya.pulsepower.home.presentation.viewModel.HomeViewModel
import ru.zzemlyanaya.pulsepower.placeSelect.presentation.ui.PlaceSelectInput
import ru.zzemlyanaya.pulsepower.uikit.*

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            HelloView(modifier = Modifier.padding(horizontal = 16.dp), "Вергилий", {})

            CurrentMembershipSection()

            MembershipBuilderSection(
                modifier = modifier.fillMaxWidth(),
                timeOfTheDayOption = viewModel.homeUiState.value.timeOfTheDayOption,
                typeOptions = viewModel.homeUiState.value.typeOptions,
                durationOptions = viewModel.homeUiState.value.durationOptions,
                onTimeOfTheDaySelect = viewModel::onTimeOfTheDaySelect,
                onTypeSelect = viewModel::onTypeSelect,
                onDurationSelect = viewModel::onDurationSelect
            )
        }

        BottomButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.pay_with),
            onClick = { /*TODO*/ }
        )
    }
}

@Composable
fun HelloView(
    modifier: Modifier = Modifier,
    name: String,
    onClick: () -> Unit
) {
    val text = buildAnnotatedString {
        pushStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface))
        append(stringResource(id = R.string.hello_world))
        pushStringAnnotation(tag = "click", annotation = "click")
        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
            append(name)
        }
        pop()
        append("!")
        pop()
    }

    ClickableText(
        modifier = modifier,
        text = text,
        onClick = { offset ->
            text.getStringAnnotations(tag = "click", start = offset, end = offset).firstOrNull()?.let { onClick() }
        },
        style = MaterialTheme.typography.headlineLarge,
    )
}

@Composable
fun CurrentMembershipSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 6.dp, end = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            text = stringResource(id = R.string.current_membership),
            style = MaterialTheme.typography.titleSmall,
            color = purple_9999ff.copy(alpha = 0.81f)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MembershipCard(
                modifier = Modifier
                    .weight(0.75f)
                    .fillMaxWidth(),
                pulseColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
                description = AnnotatedString(""),
                period = AnnotatedString(""),
                isRepeatable = false
            )

            Column(
                modifier = Modifier
                    .clickable { /*TODO*/ }
                    .weight(0.25f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
            ) {
                Text(
                    text = stringResource(id = R.string.history_short),
                    style = MaterialTheme.typography.labelSmall,
                    color = white_on_container
                )

                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.arrow_right),
                    colorFilter = ColorFilter.tint(white_on_container),
                    contentDescription = "Arrow right"
                )
            }
        }
    }
}

@Composable
fun MembershipBuilderSection(
    modifier: Modifier = Modifier,
    timeOfTheDayOption: List<OptionUiModel<TimeOfTheDay>>,
    typeOptions: List<OptionUiModel<MembershipType>>,
    durationOptions: List<OptionUiModel<MembershipDuration>>,
    onTimeOfTheDaySelect: (TimeOfTheDay) -> Unit,
    onTypeSelect: (MembershipType) -> Unit,
    onDurationSelect: (MembershipDuration) -> Unit
) {
    Column(
        modifier = modifier.padding(start = 6.dp, end = 16.dp),
    ) {
        Text(
            modifier = modifier.padding(start = 20.dp),
            text = stringResource(id = R.string.membership_builder),
            style = MaterialTheme.typography.titleSmall,
            color = blue_68a4ff.copy(alpha = 0.81f)
        )

        PulseCard(
            modifier = modifier,
            pulseColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
        ) {
            Column(
                modifier = modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(modifier = modifier) {
                    timeOfTheDayOption.forEach { item ->
                        OptionView(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            optionUiModel = item,
                            isSelected = item.isSelected,
                            onClick = { onTimeOfTheDaySelect(item.option) }
                        )
                    }
                }

                Row {
                    typeOptions.forEach { item ->
                        OptionView(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            optionUiModel = item,
                            isSelected = item.isSelected,
                            onClick = { onTypeSelect(item.option) }
                        )
                    }
                }

                Row {
                    durationOptions.forEach { item ->
                        OptionView(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            optionUiModel = item,
                            isSelected = item.isSelected,
                            onClick = { onDurationSelect(item.option) }
                        )
                    }
                }
            }
        }

        PlaceSelectInput(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "",
            onClick = { /*TODO*/ }
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                    append(stringResource(id = R.string.price_bold))
                }
                append(stringResource(id = R.string.price_rest, "49 000"))
            },
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun OptionView(
    modifier: Modifier = Modifier,
    optionUiModel: OptionUiModel<*>,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    var mModifier = modifier.clickable { onClick() }
    if (isSelected) {
        mModifier = mModifier.border(width = 1.dp, color = blue_68a4ff, shape = MaterialTheme.shapes.small)
    }

    Surface(modifier = mModifier) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            text = stringResource(id = optionUiModel.name).capitalize(Locale.current),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = if (isSelected) blue_68a4ff else white,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun OptionPreview() {
    PulsePowerTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OptionView(
                optionUiModel = OptionUiModel(
                    option = MembershipDuration.YEAR,
                    name = R.string.all_day,
                    isSelected = false
                ),
                isSelected = true,
                onClick = { }
            )

            OptionView(
                optionUiModel = OptionUiModel(
                    option = MembershipDuration.YEAR,
                    name = R.string.all_day,
                    isSelected = false
                ),
                isSelected = false,
                onClick = { }
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    PulsePowerTheme {
        HomeScreen()
    }
}