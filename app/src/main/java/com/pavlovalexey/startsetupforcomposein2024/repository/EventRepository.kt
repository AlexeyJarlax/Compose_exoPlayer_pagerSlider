package com.pavlovalexey.startsetupforcomposein2024.repository

import com.pavlovalexey.startsetupforcomposein2024.model.Event

interface EventRepository {
    suspend fun getEvents(): List<Event>
    suspend fun getEventById(id: String): Event

}