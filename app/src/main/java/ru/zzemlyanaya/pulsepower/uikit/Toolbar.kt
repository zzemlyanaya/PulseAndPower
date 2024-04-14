package ru.zzemlyanaya.pulsepower.uikit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.zzemlyanaya.pulsepower.app.theme.PulsePowerTheme

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: () -> Unit
) {
    Surface(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)
        ) {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = onBackPressed
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Arrow back"
                )
            }

            Text(
                modifier = Modifier.wrapContentWidth(),
                text = title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(widthDp = 300)
@Composable
fun ToolbarPreview() {
    PulsePowerTheme {
        Toolbar(
            title = "Title",
            onBackPressed = {}
        )
    }
}