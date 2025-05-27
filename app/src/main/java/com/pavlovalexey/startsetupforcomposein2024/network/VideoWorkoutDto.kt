package com.pavlovalexey.startsetupforcomposein2024.network
import androidx.annotation.Keep

@Keep
data class VideoWorkoutDto(
    val id: Int,
    val duration: String,
    val link: String
)