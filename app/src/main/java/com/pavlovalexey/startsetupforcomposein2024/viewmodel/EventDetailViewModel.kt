package com.pavlovalexey.startsetupforcomposein2024.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavlovalexey.startsetupforcomposein2024.model.Event
import com.pavlovalexey.startsetupforcomposein2024.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val repository: EventRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val eventId: String? = savedStateHandle["eventId"]

    private val _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        fetchEventDetails()
    }

    private fun fetchEventDetails() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                eventId?.let { id ->
                    val eventDetail = repository.getEventById(id)
                    _event.value = eventDetail
                    _uiState.value = UiState.Success(listOf(eventDetail))
                } ?: run {
                    _uiState.value = UiState.Error("Идентификатор события не предоставлен")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Ошибка загрузки деталей события: ${e.localizedMessage}")
            }
        }
    }
}