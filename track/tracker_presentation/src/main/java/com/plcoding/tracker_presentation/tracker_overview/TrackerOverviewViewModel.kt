package com.plcoding.tracker_presentation.tracker_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.preferences.Preferences
import com.plcoding.core.utils.UiEvent
import com.plcoding.tracker_domain.use_case.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    private val preferences: Preferences,
    private val useCases: TrackerUseCases
) :
    ViewModel() {
    var state by mutableStateOf(TrackerOverviewState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getFoodsForDateJob: Job? = null

    init {
        refreshFoods()
        preferences.saveShouldShowOnBoarding(false)
    }

    fun onEvent(event: TrackerOverViewEvent) {
        when (event) {

            is TrackerOverViewEvent.OnDeleteTrackedFoodClick -> {
                viewModelScope.launch {
                    useCases.deleteTrackedFoodUseCase(event.trackedFood)
                    refreshFoods()
                }
            }

            TrackerOverViewEvent.OnNextDayClick -> {
                state = state.copy(date = state.date.plusDays(1))
                refreshFoods()
            }

            TrackerOverViewEvent.OnPreviousDayClick -> {
                state = state.copy(date = state.date.minusDays(1))
                refreshFoods()
            }

            is TrackerOverViewEvent.OnToggleMealClick -> {
                state = state.copy(meals = state.meals.map { meal ->
                    if (event.meal.name == meal.name) {
                        meal.copy(isExpanded = !event.meal.isExpanded)
                    } else {
                        meal
                    }
                })
            }
        }
    }

    private fun refreshFoods() {
        getFoodsForDateJob?.cancel()
        getFoodsForDateJob = useCases.getFoodsForDateUseCase(state.date).onEach { foodList ->
            val nutrientsResult = useCases.calculateMealNutrientsUseCase(foodList)
            state = state.copy(
                totalCarbs = nutrientsResult.totalCarbs,
                totalProtein = nutrientsResult.totalProteins,
                totalFat = nutrientsResult.totalFat,
                totalCalories = nutrientsResult.totalCalories,
                carbsGoal = nutrientsResult.carbsGoal,
                proteinsGoal = nutrientsResult.proteinsGoal,
                fatGoal = nutrientsResult.fatGoal,
                caloriesGoal = nutrientsResult.caloriesGoal,
                trackedFoods = foodList,
                meals = state.meals.map {
                    val nutrientsForMeal = nutrientsResult.mealNutrients[it.mealType]
                        ?: return@map it.copy(
                            carbsAmount = 0,
                            proteinsAmount = 0,
                            fatAmount = 0,
                            calories = 0
                        )
                    it.copy(
                        carbsAmount = nutrientsForMeal.carbsAmount,
                        proteinsAmount = nutrientsForMeal.proteinsAmount,
                        fatAmount = nutrientsForMeal.fatAmount,
                        calories = nutrientsForMeal.caloriesAmount
                    )
                }

            )

        }.launchIn(viewModelScope)
    }


}