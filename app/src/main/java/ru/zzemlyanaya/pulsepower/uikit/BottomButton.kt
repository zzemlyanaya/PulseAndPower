package ru.zzemlyanaya.pulsepower.uikit

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.Shapes
import ru.zzemlyanaya.pulsepower.app.theme.darkGradient

@Composable
fun BottomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(darkGradient, MaterialTheme.shapes.medium)
    ) {
        Row {
            Text(text = text, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Preview
@Composable
fun BottomButtonPreview() {
    BottomButton(text = "Click me!", onClick = {})
}