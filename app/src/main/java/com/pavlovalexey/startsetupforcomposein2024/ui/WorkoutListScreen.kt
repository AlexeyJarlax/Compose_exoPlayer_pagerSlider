package com.pavlovalexey.startsetupforcomposein2024.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.pavlovalexey.startsetupforcomposein2024.model.Workout
import com.pavlovalexey.startsetupforcomposein2024.viewmodel.UiState
import com.pavlovalexey.startsetupforcomposein2024.viewmodel.WorkoutListViewModel
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutListScreen(
    onItemClick: (Int) -> Unit,
    viewModel: WorkoutListViewModel = hiltViewModel()
) {
    val uiState  by viewModel.uiState.collectAsState()
    val workouts by viewModel.workouts.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Тренировки") }) }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (uiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                is UiState.Success -> {
                    LazyColumn {
                        items(workouts) { w ->
                            WorkoutListItem(
                                item    = w,
                                onClick = { onItemClick(w.id) }
                            )
                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
                is UiState.Error -> {
                    Text(
                        text     = (uiState as UiState.Error).message,
                        color    = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun WorkoutListItem(item: Workout, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item.imageUrl?.let {
            Image(
                painter = rememberImagePainter(it),
                contentDescription = null,
                modifier = Modifier.size(64.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(8.dp))
        }
        Column {
            Text(item.title, style = MaterialTheme.typography.titleMedium)
            Text("${item.type} • ${item.duration} мин", style = MaterialTheme.typography.bodySmall)
        }
    }
}