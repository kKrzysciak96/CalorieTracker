package com.plcoding.tracker_domain.use_case

import com.plcoding.tracker_domain.model.MealType
import com.plcoding.tracker_domain.model.TrackableFood
import com.plcoding.tracker_domain.model.TrackedFood
import com.plcoding.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class InsertTrackedFoodUseCase(private val repository: TrackerRepository) {
    suspend operator fun invoke(
        food: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate
    ) {
        repository.insertTrackedFood(
            TrackedFood(
                name = food.name,
                carbs = ((food.carbsPer100g / 100F) * amount).roundToInt(),
                proteins = ((food.proteinsPer100g / 100F) * amount).roundToInt(),
                fat = ((food.fatPer100g / 100F) * amount).roundToInt(),
                photo = food.photo,
                mealType = mealType,
                amount = amount,
                date = date,
                calories = ((food.caloriesPer100g / 100F) * amount).roundToInt(),
            )
        )
    }
}