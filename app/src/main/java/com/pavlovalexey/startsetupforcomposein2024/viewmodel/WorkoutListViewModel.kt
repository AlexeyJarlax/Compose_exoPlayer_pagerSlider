package com.pavlovalexey.startsetupforcomposein2024.viewmodel

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
class WorkoutListViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState
    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workouts

    init {
        loadWorkouts()
    }

    private fun loadWorkouts() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val list = repository.getWorkouts()
                _workouts.value = list
                _uiState.value = UiState.Success()
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Не удалось загрузить тренировки")
            }
        }
    }
}