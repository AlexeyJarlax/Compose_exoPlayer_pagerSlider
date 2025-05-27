package com.pavlovalexey.startsetupforcomposein2024.network

import retrofit2.http.GET
import retrofit2.http.Query
import androidx.annotation.Keep

@Keep
interface WorkoutApiService {
    @GET("get_workouts")
    suspend fun getWorkouts(): List<WorkoutDto>

    @GET("get_video")
    suspend fun getVideo(@Query("id") id: Int): VideoWorkoutDto
}