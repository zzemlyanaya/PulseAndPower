package ru.zzemlyanaya.pulsepower.uikit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.*

@Composable
fun OutlinedTextInput(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String,
    onValueChanged: (String) -> Unit,
    prefix: String? = null,
    error: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true
) {
    var focusState by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .defaultMinSize(minHeight = 56.dp)
            .onFocusChanged { focus -> focusState = focus.isFocused },
        value = value,
        onValueChange = onValueChanged,
        placeholder = {
            Text(
                text = if (focusState) "" else placeholder,
                style = MaterialTheme.typography.bodyMediumOnSurface
            )
        },
        prefix = prefix?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        isError = error != null,
        supportingText = error?.let {
            {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 0.dp),
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium,
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = white_caption,
            disabledBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            errorContainerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        enabled = enabled
    )
}

@Composable
fun PhoneInput(
    modifier: Modifier = Modifier,
    value: String,
    error: String?,
    onValueChanged: (String) -> Unit,
    onDone: () -> Unit
) {
    OutlinedTextInput(
        value = value,
        onValueChanged = onValueChanged,
        placeholder = stringResource(id = R.string.phone),
        prefix = stringResource(id = R.string.phone_prefix_ru),
        error = error,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done,
            autoCorrect = false
        ),
        keyboardActions = KeyboardActions(onDone = { onDone() })
    )
}

@Preview
@Composable
fun OutlinedTextInputPreview() {
    PulsePowerTheme {
        OutlinedTextInput(value = "", prefix = "~", placeholder = "Label", onValueChanged = {})
    }
}

@Preview
@Composable
fun OutlinedTextInputDisabledPreview() {
    PulsePowerTheme {
        OutlinedTextInput(value = "Value", prefix = "~", placeholder = "Label", onValueChanged = {}, enabled = false)
    }
}

@Preview
@Composable
fun OutlinedTextInputErrorPreview() {
    PulsePowerTheme {
        OutlinedTextInput(value = "Value", prefix = "~", placeholder = "Label", onValueChanged = {}, error = "Error")
    }
}