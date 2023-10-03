package com.plcoding.tracker_presentation.tracker_overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import com.plcoding.core_ui.CarbColor
import com.plcoding.core_ui.FatColor
import com.plcoding.core_ui.ProteinColor

@Composable
fun NutrientsBar(
    carbs: Int,
    proteins: Int,
    fat: Int,
    calories: Int,
    calorieGoal: Int,
    modifier: Modifier = Modifier,
) {
    val background = MaterialTheme.colors.background
    val caloriesExceedColor = MaterialTheme.colors.error

    val carbsWidthRatio = remember { Animatable(0f) }
    val proteinsWidthRatio = remember { Animatable(0f) }
    val fatWidthRatio = remember { Animatable(0f) }

    LaunchedEffect(key1 = carbs, block = {
        carbsWidthRatio.animateTo(targetValue = (carbs * 4f) / calorieGoal)
    })
    LaunchedEffect(key1 = proteins, block = {
        proteinsWidthRatio.animateTo(targetValue = (proteins * 4f) / calorieGoal)
    })
    LaunchedEffect(key1 = fat, block = {
        fatWidthRatio.animateTo(targetValue = (fat * 9f) / calorieGoal)
    })

    Canvas(modifier = modifier, onDraw = {
        if (calories <= calorieGoal) {
            val carbsWidth = carbsWidthRatio.value * size.width
            val proteinsWidth = proteinsWidthRatio.value * size.width
            val fatWidth = fatWidthRatio.value * size.width
            drawRoundRect(color = background, size = size, cornerRadius = CornerRadius(100f))
            drawRoundRect(
                color = FatColor,
                size = Size(width = carbsWidth + proteinsWidth + fatWidth, height = size.height),
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = ProteinColor,
                size = Size(width = carbsWidth + proteinsWidth, height = size.height),
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = CarbColor,
                size = Size(width = carbsWidth, height = size.height),
                cornerRadius = CornerRadius(100f)
            )
        } else
            drawRoundRect(
                color = caloriesExceedColor,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
    })
}