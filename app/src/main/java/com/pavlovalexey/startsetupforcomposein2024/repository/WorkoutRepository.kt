package com.pavlovalexey.startsetupforcomposein2024.repository

import com.pavlovalexey.startsetupforcomposein2024.model.Workout

interface WorkoutRepository {
    suspend fun getWorkouts(): List<Workout>
    suspend fun getWorkoutById(id: Int): Workout
    suspend fun getWorkoutVideoLink(id: Int): String
}