package com.example.robogyan.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = NavyBlue,               // Main color for app bars, buttons, etc.
    secondary = Cyan,                 // Accent color for highlights, icons, buttons
    background = CharcoalBlack,               // Background color for screens
    surface = NavyBlue,      // Slightly lighter than pure black for cards/surfaces
    onPrimary = White,                // Text on primary (Navy Blue)
    onSecondary = White,              // Text on secondary (Cyan buttons)
    onBackground = White,             // Main text color on dark background
    onSurface = White                 // Text on surfaces/cards
)

private val LightColorScheme = lightColorScheme(
    primary = Black,               // Main color for app bars, buttons
    secondary = Cyan,                 // Accent color for highlights
    background = White,               // Background for light mode
    surface = Color(0xFFE0E0E0),      // Light gray surface for cards
    onPrimary = White,                // Text on primary (Navy Blue)
    onSecondary = Black,              // Text on secondary (Cyan buttons)
    onBackground = Black,             // Main text color on white background
    onSurface = Black                 // Text on surfaces/cards
)

@Composable
fun RobogyanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),  // Default dark mode
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}