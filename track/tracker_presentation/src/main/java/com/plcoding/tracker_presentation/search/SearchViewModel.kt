package com.plcoding.tracker_presentation.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.R
import com.plcoding.core.domain.use_case.FilterOutDigitsUseCase
import com.plcoding.core.utils.UiEvent
import com.plcoding.core.utils.UiText
import com.plcoding.tracker_domain.use_case.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: TrackerUseCases,
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase
) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnAmountFoodChange -> {
                state = state.copy(trackableFoodList = state.trackableFoodList.map {
                    if (it.food == event.food) {
                        it.copy(amount = filterOutDigitsUseCase(event.amount))
                    } else {
                        it
                    }
                })
            }

            is SearchEvent.OnQueryChange -> {
                state = state.copy(query = event.query)
            }

            SearchEvent.OnSearch -> {
                executeSearch()
            }

            is SearchEvent.OnSearchFocusChange -> {
                state = state.copy(isHintVisible = !event.isFocused && state.query.isBlank())
            }

            is SearchEvent.OnToggleTrackableFood -> {
                state = state.copy(trackableFoodList = state.trackableFoodList.map {
                    if (it.food == event.food) {
                        it.copy(isExpanded = !it.isExpanded)
                    } else {
                        it
                    }
                })
            }

            is SearchEvent.OnTrackFoodClick -> {
                trackFood(event)
            }
        }
    }

    private fun executeSearch() {

        viewModelScope.launch {
            state = state.copy(isSearching = true)
            useCases
                .searchFoodsForDateUseCase(state.query)
                .onSuccess { trackableFoodList ->
                    state = state.copy(
                        trackableFoodList = trackableFoodList.map {
                            TrackableFoodUiState(food = it)
                        },
                        isSearching = false,
                        query = ""
                    )
                }
                .onFailure {
                    state = state.copy(isSearching = false)
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_something_went_wrong)))
                }
        }
    }

    private fun trackFood(event: SearchEvent.OnTrackFoodClick) {
        viewModelScope.launch {
            val uiState = state.trackableFoodList.find { it.food == event.food }
            useCases.insertTrackedFoodUseCase(
                food = uiState?.food ?: return@launch,
                amount = uiState.amount.toIntOrNull() ?: return@launch,
                mealType = event.mealType,
                date = event.date
            )

            _uiEvent.send(UiEvent.NavigateUp)
        }
    }
}