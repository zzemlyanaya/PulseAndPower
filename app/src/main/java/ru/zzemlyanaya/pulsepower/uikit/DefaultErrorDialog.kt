package ru.zzemlyanaya.pulsepower.uikit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.PulsePowerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultErrorDialog() {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        BasicAlertDialog(onDismissRequest = { openDialog.value = false }) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(modifier = Modifier.padding(vertical = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(id = R.string.server_error_title),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(id = R.string.server_error_text),
                        style = MaterialTheme.typography.bodySmall
                    )
                    TextButton(
                        modifier = Modifier.align(Alignment.End).padding(end = 8.dp),
                        onClick = { openDialog.value = false }
                    ) {
                        Text(text = stringResource(id = R.string.close), style = MaterialTheme.typography.headlineMedium)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AuthScreenErrorPreview() {
    PulsePowerTheme {
        DefaultErrorDialog()
    }
}