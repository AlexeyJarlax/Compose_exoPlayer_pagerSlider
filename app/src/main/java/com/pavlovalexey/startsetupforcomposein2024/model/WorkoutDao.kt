package com.pavlovalexey.startsetupforcomposein2024.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts")
    suspend fun getAll(): List<Workout>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Workout>)

    @Query("DELETE FROM workouts")
    suspend fun clear()

    @Query("SELECT * FROM workouts WHERE id = :id")
    suspend fun getById(id: Int): Workout?
}