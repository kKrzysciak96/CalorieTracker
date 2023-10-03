package com.plcoding.calorytracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.plcoding.calorytrackerprep.ui.theme.Typography
import com.plcoding.core_ui.BrightGreen
import com.plcoding.core_ui.DarkGray
import com.plcoding.core_ui.DarkGreen
import com.plcoding.core_ui.Dimensions
import com.plcoding.core_ui.LightGray
import com.plcoding.core_ui.LocalSpacing
import com.plcoding.core_ui.MediumGray
import com.plcoding.core_ui.Orange
import com.plcoding.core_ui.TextWhite

private val DarkColorPalette = darkColors(
    primary = BrightGreen,
    primaryVariant = DarkGreen,
    secondary = Orange,
    background = MediumGray,
    onBackground = TextWhite,
    surface = LightGray,
    onSurface = TextWhite,
    onPrimary = Color.White,
    onSecondary = Color.White,
)

private val LightColorPalette = lightColors(
    primary = BrightGreen,
    primaryVariant = DarkGreen,
    secondary = Orange,
    background = Color.White,
    onBackground = DarkGray,
    surface = Color.White,
    onSurface = DarkGray,
    onPrimary = Color.White,
    onSecondary = Color.White,
)

@Composable
fun CalorieTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    CompositionLocalProvider(LocalSpacing provides Dimensions()) {

        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }

}