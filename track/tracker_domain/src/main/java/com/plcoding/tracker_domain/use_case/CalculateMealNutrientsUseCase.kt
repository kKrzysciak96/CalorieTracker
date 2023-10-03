package com.plcoding.tracker_domain.use_case


import com.plcoding.core.domain.model.ActivityLevel
import com.plcoding.core.domain.model.Gender
import com.plcoding.core.domain.model.GoalType
import com.plcoding.core.domain.model.UserInfo
import com.plcoding.core.domain.preferences.Preferences
import com.plcoding.tracker_domain.model.MealType
import com.plcoding.tracker_domain.model.TrackedFood
import kotlin.math.roundToInt

class CalculateMealNutrientsUseCase(private val preferences: Preferences) {

    operator fun invoke(trackedFood: List<TrackedFood>): Result {

        val allNutrients = trackedFood.groupBy { it.mealType }.mapValues { entry ->
            val type = entry.key
            val listOfFood = entry.value
            MealNutrients(
                carbsAmount = listOfFood.sumOf { it.carbs },
                proteinsAmount = listOfFood.sumOf { it.proteins },
                fatAmount = listOfFood.sumOf { it.fat },
                caloriesAmount = listOfFood.sumOf { it.calories },
                mealType = type
            )
        }

        val totalCarbs = allNutrients.values.sumOf { it.carbsAmount }
        val totalProteins = allNutrients.values.sumOf { it.proteinsAmount }
        val totalFat = allNutrients.values.sumOf { it.fatAmount }
        val totalCalories = allNutrients.values.sumOf { it.caloriesAmount }

        val userInfo = preferences.loadUserInfo()

        val caloriesGoal = dailyCalorieRequirement(userInfo)
        val carbsGoal = (caloriesGoal * userInfo.carbRatio / 4f).roundToInt()
        val proteinsGoal = (caloriesGoal * userInfo.proteinRatio / 4f).roundToInt()
        val fatGoal = (caloriesGoal * userInfo.carbRatio / 9f).roundToInt()

        return Result(
            carbsGoal = carbsGoal,
            proteinsGoal = proteinsGoal,
            fatGoal = fatGoal,
            caloriesGoal = caloriesGoal,
            totalCarbs = totalCarbs,
            totalProteins = totalProteins,
            totalFat = totalFat,
            totalCalories = totalCalories,
            mealNutrients = allNutrients
        )
    }

    private fun bmr(userInfo: UserInfo): Int {
        return when (userInfo.gender) {
            is Gender.Male -> {
                (66.47f + 13.75f * userInfo.weight +
                        5f * userInfo.height - 6.75f * userInfo.age).roundToInt()
            }

            is Gender.Female -> {
                (665.09f + 9.56f * userInfo.weight +
                        1.84f * userInfo.height - 4.67 * userInfo.age).roundToInt()
            }
        }
    }

    private fun dailyCalorieRequirement(userInfo: UserInfo): Int {
        val activityFactor = when (userInfo.activityLevel) {
            is ActivityLevel.Low -> 1.2f
            is ActivityLevel.Medium -> 1.3f
            is ActivityLevel.High -> 1.4f
        }
        val calorieExtra = when (userInfo.goalType) {
            is GoalType.LoseWeight -> -500
            is GoalType.KeepWeight -> 0
            is GoalType.GainWeight -> 500
        }
        return (bmr(userInfo) * activityFactor + calorieExtra).roundToInt()
    }

    data class MealNutrients(
        val carbsAmount: Int,
        val proteinsAmount: Int,
        val fatAmount: Int,
        val caloriesAmount: Int,
        val mealType: MealType
    )

    data class Result(
        val carbsGoal: Int,
        val proteinsGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,
        val totalCarbs: Int,
        val totalProteins: Int,
        val totalFat: Int,
        val totalCalories: Int,
        val mealNutrients: Map<MealType, MealNutrients>
    )

}