package com.plcoding.tracker_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class TrackedFoodEntity(
    val name: String,
    val carbs: Int,
    val proteins: Int,
    val fat: Int,
    val photo: String?,
    val type: String,
    val amount: Int,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    val calories: Int,
    @PrimaryKey val id: Int? = null
)
