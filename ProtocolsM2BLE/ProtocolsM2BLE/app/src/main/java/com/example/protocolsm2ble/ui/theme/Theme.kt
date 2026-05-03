package com.example.protocolsm2ble.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Teal500,
    primaryVariant = DeepPurple700,
    background = Color(0xFF121212), // Ensure fully opaque background
    surface = Color(0xFF121212), // Match surface color with background
    onPrimary = Color.White,
    onBackground = Color.White, // Lighten the text color
    onSurface = Color.White // Lighten the text color
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Color.White, // Ensure fully opaque background
    surface = Color.White, // Match surface color with background
    onPrimary = Color.Black, // Ensure text color contrasts with primary color
    onBackground = Color.Black, // Darken the text color for better visibility
    onSurface = Color.Black // Darken the text color for better visibility
)


@Composable
fun BluetoothChatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}