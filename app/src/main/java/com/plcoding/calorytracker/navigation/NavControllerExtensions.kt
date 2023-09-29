package com.plcoding.calorytracker.navigation

import androidx.navigation.NavController
import com.plcoding.core.utils.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    navigate(event.route)
}