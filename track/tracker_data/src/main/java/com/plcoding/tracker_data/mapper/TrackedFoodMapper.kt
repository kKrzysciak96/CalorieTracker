package com.plcoding.tracker_data.mapper

import com.plcoding.tracker_data.local.entity.TrackedFoodEntity
import com.plcoding.tracker_domain.model.MealType
import com.plcoding.tracker_domain.model.TrackedFood
import java.time.LocalDate

fun TrackedFoodEntity.toTrackedFood(): TrackedFood {
    return TrackedFood(
        name = name,
        carbs = carbs,
        proteins = proteins,
        fat = fat,
        photo = photo,
        mealType = MealType.fromString(type),
        amount = amount,
        date = LocalDate.of(year, month, dayOfMonth),
        calories = calories,
        id = id,
    )
}

fun TrackedFood.toTrackedFoodEntity(): TrackedFoodEntity {
    return TrackedFoodEntity(
        name = name,
        carbs = carbs,
        proteins = proteins,
        fat = fat,
        photo = photo,
        type = mealType.name,
        amount = amount,
        year = date.year,
        month = date.monthValue,
        dayOfMonth = date.dayOfMonth,
        calories = calories,
        id = id,
    )
}