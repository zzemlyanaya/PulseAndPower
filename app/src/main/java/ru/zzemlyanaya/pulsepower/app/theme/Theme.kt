package ru.zzemlyanaya.pulsepower.app.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    background = mate_black,
    onBackground = white,
    surface = mate_black,
    onSurface = white,
    primary = blue_68a4ff,
    onPrimary = white,
    secondary = purple_9999ff,
    onSecondary = white,
    tertiary = purple_9999ff,
    error = red_error,
    onError = white,
    surfaceContainer = mate_black_container,
    surfaceContainerHighest = mate_black_variant,
    onPrimaryContainer = white,
    onSecondaryContainer = white
)

@Composable
fun PulsePowerTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DarkColorScheme.background.toArgb()
            window.navigationBarColor = DarkColorScheme.surfaceContainerHighest.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content,
        shapes = Shapes
    )
}