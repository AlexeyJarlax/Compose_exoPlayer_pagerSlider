package com.pavlovalexey.startsetupforcomposein2024.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavlovalexey.startsetupforcomposein2024.repository.WorkoutRepository
import com.pavlovalexey.startsetupforcomposein2024.model.Workout
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
    private val workoutId: Int = checkNotNull(savedStateHandle["id"])

    private val _uiState    = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _workout    = MutableStateFlow<Workout?>(null)
    val workout: StateFlow<Workout?> = _workout

    private val _videoUrl   = MutableStateFlow<String?>(null)
    val videoUrl: StateFlow<String?> = _videoUrl

    init {
        loadWorkout()
    }

    private fun loadWorkout() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val w = repository.getWorkoutById(workoutId)
                _workout.value = w
                _uiState.value = UiState.Success()
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Не удалось загрузить тренировку")
            }
        }
    }

    fun loadVideo() {
        viewModelScope.launch {
            try {
                val link = repository.getWorkoutVideoLink(workoutId)
                _videoUrl.value = link
            } catch (e: Exception) {
            }
        }
    }
}