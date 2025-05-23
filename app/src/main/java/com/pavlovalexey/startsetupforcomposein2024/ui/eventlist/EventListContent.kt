package com.pavlovalexey.startsetupforcomposein2024.ui.eventlist

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pavlovalexey.startsetupforcomposein2024.model.Event
import com.pavlovalexey.startsetupforcomposein2024.viewmodel.UiState

@Composable
fun EventListContent(
    uiState: UiState,
    events: List<Event>,
    context: Context,
    onItemClick: (String) -> Unit
) {
    when (uiState) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        is UiState.Success -> {
            if (events.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Нет доступных событий.",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(events) { event ->
                        EventListItem(
                            event = event,
                            onItemClick = { onItemClick(event.id) },
                            context = context
                        )
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }

        is UiState.Error -> {
            val message = uiState.message
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = message,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}