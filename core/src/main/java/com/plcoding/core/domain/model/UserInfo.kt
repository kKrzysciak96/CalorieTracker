package com.plcoding.core.domain.model

data class UserInfo(
    val gender: Gender,
    val age: Int,
    val weight: Float,
    val height: Int,
    val goalType: GoalType,
    val activityLevel: ActivityLevel,
    val carbRatio: Float,
    val proteinRatio: Float,
    val fatRatio: Float
)
