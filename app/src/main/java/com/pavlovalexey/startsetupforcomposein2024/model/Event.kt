package com.pavlovalexey.startsetupforcomposein2024.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val type: String, //"Мастер-класс по "
    val date: String, //"2024-12-31"
    val time: String, // "14:00"
    val location: Location,
    val imageUrl: String?,
    val distance: Double = 0.0 // в километрах
)

data class Location(
    val latitude: Double,
    val longitude: Double
)