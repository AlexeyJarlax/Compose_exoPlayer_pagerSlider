package com.pavlovalexey.startsetupforcomposein2024.network

import com.pavlovalexey.startsetupforcomposein2024.model.Event
import retrofit2.http.GET

interface EventApiService {
    @GET("events")
    suspend fun getEvents(): List<Event>
}