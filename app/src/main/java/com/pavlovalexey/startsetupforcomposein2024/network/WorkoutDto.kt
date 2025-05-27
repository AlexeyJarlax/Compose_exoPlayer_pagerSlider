package com.pavlovalexey.startsetupforcomposein2024.network
import androidx.annotation.Keep

@Keep
data class WorkoutDto(
    val id: Int,
    val title: String,
    val description: String?,
    val type: Int,
    val duration: String
)