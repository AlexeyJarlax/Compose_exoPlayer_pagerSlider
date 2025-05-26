package com.pavlovalexey.startsetupforcomposein2024.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavlovalexey.startsetupforcomposein2024.model.Workout
import com.pavlovalexey.startsetupforcomposein2024.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutDetailViewModel @Inject constructor(
    private val repository: WorkoutRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState    = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _workout    = MutableStateFlow<Workout?>(null)
    val workout: StateFlow<Workout?> = _workout

    init {
        val id: Int? = savedStateHandle["id"]
        if (id != null) {
            loadWorkout(id)
        } else {
            _uiState.value = UiState.Error("Идентификатор тренировки не задан")
        }
    }

    internal fun loadWorkout(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val w = repository.getWorkoutById(id)
                _workout.value = w
                _uiState.value = UiState.Success()
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Не удалось загрузить данные")
            }
        }
    }
}