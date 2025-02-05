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
    primary = ElectricBlue,           // For primary UI elements like buttons
    secondary = NeonGreen,            // For accents and highlights
    background = CharcoalBlack,       // Background color
    surface = GunmetalGray,           // For cards, surfaces, etc.
    onPrimary = PureWhite,            // Text on primary color (ElectricBlue)
    onSecondary = PureWhite,          // Text on secondary color (NeonGreen)
    onBackground = PureWhite,         // Text on dark background (CharcoalBlack)
    onSurface = LightGray             // Text on surfaces (GunmetalGray)
)

private val LightColorScheme = lightColorScheme(
    primary = ElectricBlue,           // For primary UI elements like buttons
    secondary = Cyan,                 // For accents and highlights
    background = PureWhite,          // Background color
    surface = Color(0xFFF5F5F5),      // For cards, surfaces, etc.
    onPrimary = PureWhite,            // Text on primary color (ElectricBlue)
    onSecondary = DarkSlateGray,      // Text on secondary color (Cyan)
    onBackground = DarkSlateGray,     // Text on light background (PureWhite)
    onSurface = DarkSlateGray         // Text on surfaces (light grayish background)
)

@Composable
fun RobogyanTheme(
    darkTheme: Boolean = true,  // Default dark mode
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}