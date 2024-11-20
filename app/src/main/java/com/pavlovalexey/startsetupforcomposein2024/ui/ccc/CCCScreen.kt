package com.pavlovalexey.startsetupforcomposein2024.ui.ccc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NotificationsScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text(text = "Это C", style = MaterialTheme.typography.headlineMedium)
    }
}