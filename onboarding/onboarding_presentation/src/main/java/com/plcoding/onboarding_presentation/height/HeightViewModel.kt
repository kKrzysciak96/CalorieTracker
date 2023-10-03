package com.plcoding.onboarding_presentation.height

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.R
import com.plcoding.core.domain.preferences.Preferences
import com.plcoding.core.domain.use_case.FilterOutDigitsUseCase
import com.plcoding.core.utils.UiEvent
import com.plcoding.core.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeightViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase
) : ViewModel() {

    var height by mutableStateOf("180")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onHeightEnter(height: String) {
        if (height.length <= 3) {
            this.height = filterOutDigitsUseCase(height)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val heightNumber: Int = height.toIntOrNull() ?: run {
                _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_height_cant_be_empty)))
                return@launch
            }
            preferences.saveHeight(heightNumber)
            _uiEvent.send(UiEvent.Success)
        }
    }
}