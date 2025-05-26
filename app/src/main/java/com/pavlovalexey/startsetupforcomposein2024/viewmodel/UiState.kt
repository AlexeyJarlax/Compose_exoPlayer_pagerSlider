package com.pavlovalexey.startsetupforcomposein2024.viewmodel

sealed class UiState {
    object Loading : UiState()
    data class Success(val message: String? = null) : UiState()
    data class Error(val message: String) : UiState()
}