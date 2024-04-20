@file:OptIn(ExperimentalMaterial3Api::class)

package ru.zzemlyanaya.pulsepower.feature.home.presentation.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.*
import ru.zzemlyanaya.pulsepower.core.ui.BaseScreen
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.feature.history.domain.model.MembershipDuration
import ru.zzemlyanaya.pulsepower.feature.home.presentation.model.OptionUiModel
import ru.zzemlyanaya.pulsepower.feature.home.presentation.model.PaymentState
import ru.zzemlyanaya.pulsepower.feature.home.presentation.model.contract.HomeContract
import ru.zzemlyanaya.pulsepower.feature.home.presentation.viewModel.HomeViewModel
import ru.zzemlyanaya.pulsepower.feature.placeSelect.presentation.ui.PlaceSelectInput
import ru.zzemlyanaya.pulsepower.uikit.*

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<HomeViewModel>()

    BaseScreen<HomeContract.UiState>(
        modifier = modifier,
        uiFlow = viewModel.screenState,
        sendIntent = viewModel::sendIntent,
        dataContent = { mModifier, uiState, sendIntent -> HomeScreen(mModifier, uiState, sendIntent) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    uiState: HomeContract.UiState,
    sendIntent: (BaseIntent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (uiState.paymentState != PaymentState.NONE) {
            PaymentStateDialog(state = uiState.paymentState, sheetState = sheetState) { sendIntent(HomeContract.Intent.ClosePaymentDialog) }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            HelloView(modifier = Modifier.padding(horizontal = 16.dp), uiState.userName) {
                sendIntent(HomeContract.Intent.OpenProfile)
            }

            CurrentMembershipSection(uiState.currentMembership) { sendIntent(HomeContract.Intent.OpenHistory) }

            MembershipBuilderSection(
                modifier = Modifier.fillMaxWidth(),
                uiState = uiState,
                sendIntent = sendIntent,
            )
        }

        BottomButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.pay_with),
            icon = ImageVector.vectorResource(id = R.drawable.yapay_logo),
            onClick = { sendIntent(HomeContract.Intent.Pay) }
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
fun CurrentMembershipSection(text: AnnotatedString, onHistoryClick: () -> Unit) {
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
                    .weight(0.8f)
                    .fillMaxWidth(),
                pulseColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
                description = text,
                isRepeatable = false
            )

            Column(
                modifier = Modifier
                    .clickable { onHistoryClick() }
                    .weight(0.2f)
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
    uiState: HomeContract.UiState,
    sendIntent: (BaseIntent) -> Unit
) {
    Column(
        modifier = modifier.padding(start = 6.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Column {
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
                        uiState.timeOfTheDayOption.forEach { item ->
                            OptionView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                optionUiModel = item,
                                isSelected = item.isSelected,
                                onClick = { sendIntent(HomeContract.Intent.SelectTimeOfTheDay(item.option)) }
                            )
                        }
                    }

                    Row {
                        uiState.typeOptions.forEach { item ->
                            OptionView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                optionUiModel = item,
                                isSelected = item.isSelected,
                                onClick = { sendIntent(HomeContract.Intent.SelectType(item.option)) }
                            )
                        }
                    }

                    Row {
                        uiState.durationOptions.forEach { item ->
                            OptionView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                optionUiModel = item,
                                isSelected = item.isSelected,
                                onClick = { sendIntent(HomeContract.Intent.SelectDuration(item.option)) }
                            )
                        }
                    }
                }
            }
        }

        PlaceSelectInput(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = uiState.favouritePlaces,
            onClick = { sendIntent(HomeContract.Intent.SelectPlace) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                    append(stringResource(id = R.string.price_bold))
                }
                append(stringResource(id = R.string.price_rest, uiState.price))
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = stringResource(id = optionUiModel.name).capitalize(Locale.current),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = if (isSelected) blue_68a4ff else white,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentStateDialog(state: PaymentState, sheetState: SheetState, onDismiss: () -> Unit) {
    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        when (state) {
            PaymentState.IN_PROGRESS -> PaymentLoadingView()
            PaymentState.SUCCESS -> PaymentSuccessView()
            PaymentState.REJECTED -> PaymentRejectedView()
            else -> {}
        }
    }
}

@Composable
fun PaymentLoadingView() {
    val infiniteTransition = rememberInfiniteTransition(label = "Pulse animation state")

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Pulsation animation"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 64.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.scale(scale)) {
            Surface(
                modifier = Modifier
                    .size(100.dp)
                    .blur(radius = 5.dp)
                    .padding(5.dp),
//                    .background(lightGradient, CircleShape),
                color = purple_9999ff,
                shape = CircleShape,
                content = {}
            )
        }

        Text(text = stringResource(id = R.string.payment_loading))
    }
}

@Composable
fun PaymentSuccessView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 64.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier
                    .size(120.dp)
                    .blur(radius = 5.dp)
                    .padding(5.dp),
                color = green_success,
                shape = CircleShape
            ) {}

            Icon(
                modifier = Modifier.size(76.dp),
                imageVector = Icons.Rounded.Check,
                tint = white,
                contentDescription = "Payment success"
            )
        }

        Text(text = stringResource(id = R.string.payment_success), textAlign = TextAlign.Center)
    }
}

@Composable
fun PaymentRejectedView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 64.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier
                    .size(120.dp)
                    .blur(radius = 5.dp)
                    .padding(5.dp),
                color = red_error,
                shape = CircleShape
            ) {}

            Icon(
                modifier = Modifier.size(76.dp),
                imageVector = Icons.Rounded.Close,
                tint = white,
                contentDescription = "Payment rejected"
            )
        }

        Text(text = stringResource(id = R.string.payment_error), textAlign = TextAlign.Center)
    }
}

@Preview
@Composable
fun PaymentDialogPreview() {
    val sheetState = rememberModalBottomSheetState()

    PulsePowerTheme {
        PaymentStateDialog(state = PaymentState.REJECTED, sheetState = sheetState) {}
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
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            uiState = HomeContract.UiState(),
            {}
        )
    }
}