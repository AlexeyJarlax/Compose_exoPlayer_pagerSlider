package com.pavlovalexey.startsetupforcomposein2024.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pavlovalexey.startsetupforcomposein2024.model.Event
import com.pavlovalexey.startsetupforcomposein2024.network.EventApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.pavlovalexey.startsetupforcomposein2024.model.EventDao

class EventRepositoryImpl @Inject constructor(
    private val apiService: EventApiService,
    @ApplicationContext private val context: Context,
    private val eventDao: EventDao
) : EventRepository {

    private val gson = Gson()

    override suspend fun getEvents(): List<Event> {
        return withContext(Dispatchers.IO) {
            try {
                val eventsFromApi = apiService.getEvents()
                eventDao.deleteAll()
                eventDao.insertAll(eventsFromApi)
                eventsFromApi
            } catch (e: Exception) {
                val cachedEvents = eventDao.getAllEvents()
                if (cachedEvents.isNotEmpty()) {
                    cachedEvents
                } else {
                    loadLocalEvents()
                }
            }
        }
    }

    override suspend fun getEventById(id: String): Event {
        return withContext(Dispatchers.IO) {
            eventDao.getEventById(id) ?: throw Exception("Событие с id $id не найдено")
        }
    }

    private suspend fun loadLocalEvents(): List<Event> {
        return withContext(Dispatchers.IO) {
            try {
                val jsonString = context.assets.open("events.json").bufferedReader().use { it.readText() }
                val listEventType = object : TypeToken<List<Event>>() {}.type
                val events: List<Event> = gson.fromJson(jsonString, listEventType)
                eventDao.insertAll(events)
                events
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}