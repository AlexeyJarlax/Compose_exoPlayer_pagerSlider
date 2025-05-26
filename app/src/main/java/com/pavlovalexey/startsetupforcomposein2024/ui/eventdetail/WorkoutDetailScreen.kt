package com.pavlovalexey.startsetupforcomposein2024.ui.eventdetail

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.pavlovalexey.startsetupforcomposein2024.viewmodel.UiState
import com.pavlovalexey.startsetupforcomposein2024.viewmodel.WorkoutDetailViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailScreen(
    workoutId: Int,
    navController: NavHostController,
    viewModel: WorkoutDetailViewModel = hiltViewModel()
) {
    val uiState  by viewModel.uiState.collectAsState()
    val workout  by viewModel.workout.collectAsState()
    val videoUrl by viewModel.videoUrl.collectAsState()

    LaunchedEffect(videoUrl) {
        videoUrl?.let { url ->
            navController.navigate("video_player/${Uri.encode(url)}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали тренировки") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
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
                is UiState.Error -> {
                    Text(
                        text     = (uiState as UiState.Error).message,
                        color    = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is UiState.Success -> {
                    workout?.let { w ->
                        Column(
                            Modifier
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp)
                        ) {
                            w.imageUrl?.let { img ->
                                Image(
                                    painter = rememberImagePainter(img),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(240.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(Modifier.height(16.dp))
                            }
                            Text(w.title, style = MaterialTheme.typography.headlineSmall)
                            Spacer(Modifier.height(8.dp))
                            Text(w.description, style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.height(8.dp))
                            Text("Тип: ${w.type}", style = MaterialTheme.typography.bodySmall)
                            Text("Длительность: ${w.duration} мин", style = MaterialTheme.typography.bodySmall)
                            Spacer(Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.loadVideo() },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Воспроизвести видео")
                            }
                        }
                    }
                }
            }
        }
    }
}