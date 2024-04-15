package ru.zzemlyanaya.pulsepower.profile.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.zzemlyanaya.pulsepower.BuildConfig
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.PulsePowerTheme
import ru.zzemlyanaya.pulsepower.core.ui.BaseScreen
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.profile.presentation.model.contract.ProfileContract
import ru.zzemlyanaya.pulsepower.profile.presentation.viewModel.ProfileViewModel
import ru.zzemlyanaya.pulsepower.uikit.Toolbar

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<ProfileViewModel>()

    BaseScreen<ProfileContract.UiState>(
        modifier = modifier,
        uiFlow = viewModel.screenState,
        sendIntent = viewModel::sendIntent,
        dataContent = { mModifier, _, sendIntent -> ProfileScreen(modifier = mModifier, sendIntent = sendIntent) }
    )
}

@Composable
fun ProfileScreen(
    modifier: Modifier,
    sendIntent: (BaseIntent) -> Unit
) {
    val uriHandler = LocalUriHandler.current
    val companySite = stringResource(id = R.string.company_site)
    val devSite = stringResource(id = R.string.dev_site)

    Column(
        modifier = modifier.padding(start = 16.dp, top = 24.dp, bottom = 20.dp, end = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Toolbar(title = stringResource(id = R.string.profile), onBackPressed = { sendIntent(BaseIntent.Back) })

            Column(modifier = Modifier.fillMaxWidth()) {
                MenuItem(
                    name = stringResource(id = R.string.my_info),
                    onClick = { sendIntent(ProfileContract.Intent.OpenMyInfo) },
                    icon = ImageVector.vectorResource(id = R.drawable.chevron_right)
                )
                MenuItem(
                    name = stringResource(id = R.string.history),
                    onClick = { sendIntent(ProfileContract.Intent.OpenHistory) },
                    icon = ImageVector.vectorResource(id = R.drawable.chevron_right)
                )
                MenuItem(
                    name = stringResource(id = R.string.about_pulse_power),
                    onClick = { uriHandler.openUri(companySite) },
                    isLink = true,
                    icon = ImageVector.vectorResource(id = R.drawable.link_external)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val text = buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                    append(stringResource(id = R.string.made_in))
                }
                pushStringAnnotation(tag = "click", annotation = "click")
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(stringResource(id = R.string.dungeons))
                }
                pop()
            }

            ClickableText(
                text = text,
                onClick = { uriHandler.openUri(devSite) },
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = stringResource(id = R.string.version, BuildConfig.VERSION_NAME),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    name: String,
    onClick: () -> Unit,
    icon: ImageVector,
    isLink: Boolean = false
) {
    Surface(modifier = modifier) {
        Column {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { onClick() }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isLink) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )

                Icon(
                    imageVector = icon,
                    contentDescription = "Arrow right",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview(device = Devices.PIXEL_4A, heightDp = 750)
@Composable
fun ProfilePreviewScreen(
    modifier: Modifier = Modifier
) {
    PulsePowerTheme {
        ProfileScreen( Modifier.fillMaxSize(), {})
    }
}