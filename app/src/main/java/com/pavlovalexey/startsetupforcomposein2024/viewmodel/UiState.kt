package com.pavlovalexey.startsetupforcomposein2024.viewmodel

import com.pavlovalexey.startsetupforcomposein2024.model.Event

sealed class UiState {
    object Loading : UiState()
    data class Success(val data: List<Event>) : UiState()
    data class Error(val message: String) : UiState()
}

// определил одну State на все вьюхи, обратить внимание на следующие вьюхи о помещении в эту же директорию