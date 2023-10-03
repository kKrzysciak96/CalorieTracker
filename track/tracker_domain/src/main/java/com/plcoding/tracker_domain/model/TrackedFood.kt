package com.plcoding.tracker_domain.model

import java.time.LocalDate

data class TrackedFood(
    val name: String,
    val carbs: Int,
    val proteins: Int,
    val fat: Int,
    val photo: String?,
    val mealType: MealType,
    val amount: Int,
    val date: LocalDate,
    val calories: Int,
    val id: Int? = null
)


sealed class MealType(val name: String) {
    object Breakfast : MealType("breakfast")
    object Lunch : MealType("lunch")
    object Dinner : MealType("dinner")
    object Snack : MealType("snack")

    companion object {
        fun fromString(name: String): MealType {
            return when (name) {
                "breakfast" -> Breakfast
                "lunch" -> Lunch
                "dinner" -> Dinner
                "snack" -> Snack
                else -> Breakfast
            }
        }
    }
}
