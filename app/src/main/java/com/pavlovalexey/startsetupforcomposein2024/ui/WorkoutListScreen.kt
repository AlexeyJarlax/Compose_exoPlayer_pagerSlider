package com.pavlovalexey.startsetupforcomposein2024.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavlovalexey.startsetupforcomposein2024.ui.eventlist.SliderPagerIndicator
import com.pavlovalexey.startsetupforcomposein2024.ui.eventlist.WorkoutListItem
import com.pavlovalexey.startsetupforcomposein2024.ui.theme.AlexSearchTextField
import com.pavlovalexey.startsetupforcomposein2024.viewmodel.UiState
import com.pavlovalexey.startsetupforcomposein2024.viewmodel.WorkoutListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutListScreen(
    onItemClick: (Int) -> Unit,
    viewModel: WorkoutListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val workouts by viewModel.workouts.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<String?>(null) }

    Scaffold { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                is UiState.Success -> {
                    val types = workouts.map { it.type }.distinct()
                    val filtered = workouts.filter { w ->
                        (searchQuery.isBlank() || w.title.contains(searchQuery, ignoreCase = true)) &&
                                (selectedType == null || w.type == selectedType)
                    }

                    Column {
                        if (workouts.isNotEmpty()) {
                            SliderPagerIndicator(
                                workouts = workouts,
                                onItemClick = onItemClick,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        AlexSearchTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholderText = "Поиск тренировок"
                        )

                        if (types.isNotEmpty()) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                FilterChip(
                                    selected = selectedType == null,
                                    onClick = { selectedType = null },
                                    label = { Text("Все") }
                                )
                                types.forEach { type ->
                                    FilterChip(
                                        selected = selectedType == type,
                                        onClick = { selectedType = type },
                                        label = { Text(type) }
                                    )
                                }
                            }
                        }

                        LazyColumn(
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            items(filtered) { w ->
                                WorkoutListItem(
                                    item = w,
                                    onClick = { onItemClick(w.id) }
                                )
                                HorizontalDivider()
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    Text(
                        text = (uiState as UiState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}