package com.plcoding.core.domain.model

sealed class GoalType(val name: String) {
    object LoseWeight : GoalType(name = "loose_weight")
    object KeepWeight : GoalType(name = "keep_weight")
    object GainWeight : GoalType(name = "gain_weight")


    companion object {
        fun fromString(name: String): GoalType {
            return when (name) {
                "loose_weight" -> LoseWeight
                "keep_weight" -> KeepWeight
                "gain_weight" -> GainWeight
                else -> KeepWeight
            }
        }
    }
}
