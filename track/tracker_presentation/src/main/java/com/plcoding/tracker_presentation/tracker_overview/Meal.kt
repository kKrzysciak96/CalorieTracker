package com.plcoding.tracker_presentation.tracker_overview


import androidx.annotation.DrawableRes
import com.plcoding.core.R
import com.plcoding.core.utils.UiText
import com.plcoding.tracker_domain.model.MealType

data class Meal(
    val name: UiText,
    @DrawableRes val drawable: Int,
    val mealType: MealType,
    val carbsAmount: Int = 0,
    val proteinsAmount: Int = 0,
    val fatAmount: Int = 0,
    val calories: Int = 0,
    val isExpanded: Boolean = false

)

val defaultMeals = listOf(
    Meal(
        name = UiText.StringResource(R.string.breakfast),
        drawable = R.drawable.ic_breakfast,
        mealType = MealType.Breakfast
    ), Meal(
        name = UiText.StringResource(R.string.lunch),
        drawable = R.drawable.ic_lunch,
        mealType = MealType.Lunch
    ), Meal(
        name = UiText.StringResource(R.string.dinner),
        drawable = R.drawable.ic_dinner,
        mealType = MealType.Dinner
    ), Meal(
        name = UiText.StringResource(R.string.snacks),
        drawable = R.drawable.ic_snack,
        mealType = MealType.Snack
    )
)