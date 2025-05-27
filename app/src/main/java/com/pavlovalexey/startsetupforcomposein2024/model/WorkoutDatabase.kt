package com.pavlovalexey.startsetupforcomposein2024.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.annotation.Keep

@Keep
@Database(entities = [Workout::class], version = 1, exportSchema = false)
abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
}