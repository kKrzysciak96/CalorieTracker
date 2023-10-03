package com.plcoding.tracker_domain.model

data class TrackableFood(
    val name: String,
    val photo: String?,
    val caloriesPer100g: Int,
    val carbsPer100g: Int,
    val proteinsPer100g: Int,
    val fatPer100g: Int
)
