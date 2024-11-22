package com.pavlovalexey.startsetupforcomposein2024.ui.eventlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavlovalexey.startsetupforcomposein2024.viewmodel.EventListViewModel
import com.pavlovalexey.startsetupforcomposein2024.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    onItemClick: (String) -> Unit,
    onCancel: () -> Unit,
    viewModel: EventListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val events by viewModel.filteredEvents.collectAsState()
    var showFilterDialog by remember { mutableStateOf(false) }

    if (showFilterDialog) {
        FilterDialog(
            onDismiss = { showFilterDialog = false },
            onApply = { type, date, distance ->
                viewModel.setFilterType(type)
                viewModel.setFilterDate(date)
                viewModel.setFilterDistance(distance)
                showFilterDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("События рядом") },
                actions = {
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(
                            imageVector = Icons.Filled.FilterList,
                            contentDescription = "Фильтры"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showFilterDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = "Фильтры"
                )
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (uiState) {
                    is UiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is UiState.Success -> {
                        if (events.isEmpty()) {
                            Text(
                                text = "Нет доступных событий.",
                                modifier = Modifier.align(Alignment.Center),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
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
                        val message = (uiState as UiState.Error).message
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
    )
}