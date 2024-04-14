package ru.zzemlyanaya.pulsepower.uikit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.*
import java.util.UUID

@Composable
fun Logo(modifier: Modifier = Modifier, mode: String = LOGO_CENTERED) {
    val aligment = if (mode == LOGO_CENTERED) Alignment.Center else Alignment.CenterStart
    val textAlign = if (mode == LOGO_CENTERED) TextAlign.Center else TextAlign.Start
    val paddings = if (mode == LOGO_CENTERED) PaddingValues(top = 16.dp) else PaddingValues(start = 52.dp)

    Box(contentAlignment = aligment) {
        Image(
            painter = painterResource(R.drawable.pp_logo),
            contentDescription = "Pulse&Power Logo",
            modifier = modifier.size(117.dp, 103.dp)
        )

        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.moto_part_1))

                withStyle(style = SpanStyle(color = blue_68a4ff)) {
                    append(stringResource(id = R.string.moto_part_blue))
                }
                append(stringResource(id = R.string.moto_part_2))

                withStyle(style = SpanStyle(color = purple_9999ff)) {
                    append(stringResource(id = R.string.moto_part_purple))
                }
                append(stringResource(id = R.string.moto_part_3))
            },
            textAlign = textAlign,
            modifier = modifier
                .fillMaxWidth()
                .padding(paddings),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(widthDp = 500)
@Composable
fun LogoPreview() {
    PulsePowerTheme {
        Surface {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Logo(mode = LOGO_CENTERED)
                Logo(mode = LOGO_AT_START)
            }
        }
    }
}

val LOGO_CENTERED = UUID.randomUUID().toString()
val LOGO_AT_START = UUID.randomUUID().toString()