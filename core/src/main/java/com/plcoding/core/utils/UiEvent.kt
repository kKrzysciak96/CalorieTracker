package com.plcoding.core.utils

sealed class UiEvent {
    object Success : UiEvent()
    object NavigateUp : UiEvent()
    data class ShowSnackBar(val message: UiText) : UiEvent()
}