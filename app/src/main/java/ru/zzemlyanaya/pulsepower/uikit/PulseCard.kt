package ru.zzemlyanaya.pulsepower.uikit

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.zzemlyanaya.pulsepower.R
import ru.zzemlyanaya.pulsepower.app.theme.*

@Composable
fun PulseCard(
    modifier: Modifier = Modifier,
    pulseColor: Color,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .padding(10.dp)
            .shadow(
                color = pulseColor,
                offsetX = 0.dp,
                offsetY = 0.dp,
                blurRadius = 14.dp
            )
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        content()
    }
}

@Composable
fun MembershipCard(
    modifier: Modifier = Modifier,
    pulseColor: Color,
    description: AnnotatedString,
    period: AnnotatedString,
    isRepeatable: Boolean,
    onRepeat: () -> Unit = {}
) {
    PulseCard(pulseColor = pulseColor, modifier = modifier) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = description + AnnotatedString("\n\n") + period,
                style = MaterialTheme.typography.bodySmall
            )

            if (isRepeatable) {
                IconButton(onClick = onRepeat) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .rotate(90f),
                        imageVector = ImageVector.vectorResource(R.drawable.repeat),
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "Button repeat"
                    )
                }
            }
        }
    }
}

@Preview(widthDp = 300)
@Composable
fun PulseCardPreview() {
    PulsePowerTheme {
        PulseCard(
            pulseColor = blue_68a4ff.copy(alpha = 0.5f),
            content = {
                Box(modifier = Modifier.height(100.dp))
            }
        )
    }
}

@Preview(widthDp = 300)
@Composable
fun SubscriptionHistoryViewPreview() {
    PulsePowerTheme {
        MembershipCard(
            pulseColor = white.copy(alpha = 0.5f),
            description = AnnotatedString("Индивидуальные утренние занятия"),
            period = AnnotatedString("На 3 месяца"),
            isRepeatable = true,
            onRepeat = {}
        )
    }
}

@Preview(widthDp = 300)
@Composable
fun SubscriptionActiveViewPreview() {
    PulsePowerTheme {
        MembershipCard(
            pulseColor = purple_9999ff.copy(alpha = 0.5f),
            description = AnnotatedString("Индивидуальные утренние занятия"),
            period = AnnotatedString("На 3 месяца до 11.06.2024"),
            isRepeatable = false
        )
    }
}

fun Modifier.shadow(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
) = then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter = (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }
            frameworkPaint.color = color.toArgb()

            val leftPixel = offsetX.toPx()
            val topPixel = offsetY.toPx()
            val rightPixel = size.width + topPixel
            val bottomPixel = size.height + leftPixel

            canvas.drawRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                paint = paint,
            )
        }
    }
)