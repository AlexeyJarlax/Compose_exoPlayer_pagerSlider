package com.pavlovalexey.startsetupforcomposein2024.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavlovalexey.startsetupforcomposein2024.model.Event
import com.pavlovalexey.startsetupforcomposein2024.repository.EventRepository
import com.pavlovalexey.startsetupforcomposein2024.repository.LocationManager
import com.pavlovalexey.startsetupforcomposein2024.repository.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val repository: EventRepository,
    private val locationManager: LocationManager,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    private val _filteredEvents = MutableStateFlow<List<Event>>(emptyList())
    val filteredEvents: StateFlow<List<Event>> = _filteredEvents.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _filterType = MutableStateFlow<String?>(null)
    private val _filterDate = MutableStateFlow<String?>(null)
    private val _filterDistance = MutableStateFlow<Double?>(null)

    init {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val eventList = repository.getEvents()
                _events.value = eventList
                _filteredEvents.value = eventList
                _uiState.value = UiState.Success(eventList)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Ошибка загрузки событий: ${e.localizedMessage}")
            }
        }
        setupFilters()
        getCurrentLocation()
    }

    private fun getCurrentLocation() {
        viewModelScope.launch {
            locationManager.getCurrentLocation().collect { location ->
                location?.let {
                    updateEventDistances(it.latitude, it.longitude)
                }
            }
        }
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            val eventList = repository.getEvents()
            _events.value = eventList
            _filteredEvents.value = eventList
        }
    }

    private fun setupFilters() {
        viewModelScope.launch {
            combine(
                _events,
                _filterType,
                _filterDate,
                _filterDistance
            ) { events, type, date, distance ->
                events.filter { event ->
                    val matchesType = type?.let { event.type.equals(it, ignoreCase = true) } ?: true
                    val matchesDate = date?.let { event.date == it } ?: true
                    // типа расстояние уже рассчитано и хранится в Event
                    val matchesDistance = distance?.let { event.distance <= it } ?: true
                    matchesType && matchesDate && matchesDistance
                }
            }.collect { filtered ->
                _filteredEvents.value = filtered
            }
        }
    }

    fun setFilterType(type: String?) {
        _filterType.value = type
    }

    fun setFilterDate(date: String?) {
        _filterDate.value = date
    }

    fun setFilterDistance(distance: Double?) {
        _filterDistance.value = distance
    }

    fun updateEventDistances(currentLatitude: Double, currentLongitude: Double) {
        viewModelScope.launch {
            val updatedEvents = _events.value.map { event ->
                val distance = calculateDistance(
                    currentLatitude,
                    currentLongitude,
                    event.location.latitude,
                    event.location.longitude
                )
                event.copy(distance = distance)
            }
            _events.value = updatedEvents
        }
    }

    private fun calculateDistance(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        val earthRadius = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return earthRadius * c
    }
}