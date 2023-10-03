package com.plcoding.tracker_data.local.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.tracker_data.local.TrackerDao

@Database(entities = [TrackedFoodEntity::class], version = 1)
abstract class TrackerDataBase : RoomDatabase() {

    abstract fun provideDao(): TrackerDao
}