package com.pavlovalexey.startsetupforcomposein2024.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val type: String,
    val duration: Int,
    val imageUrl: String?
)