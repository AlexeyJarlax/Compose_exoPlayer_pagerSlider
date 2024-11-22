package com.pavlovalexey.startsetupforcomposein2024.ui.eventdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.pavlovalexey.startsetupforcomposein2024.model.Event
import com.pavlovalexey.startsetupforcomposein2024.viewmodel.UiState
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavlovalexey.startsetupforcomposein2024.ui.theme.CustomButtonOne
import com.pavlovalexey.startsetupforcomposein2024.utils.ToastExt
import com.pavlovalexey.startsetupforcomposein2024.viewmodel.EventDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    navController: NavHostController,
    viewModel: EventDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val event by viewModel.event.collectAsState()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали события") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
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
                        event?.let {
                            EventDetailContent(event = it, onAddToCalendar = { addToCalendar(context, it) })
                        } ?: run {
                            Text(
                                text = "Событие не найдено",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    is UiState.Error -> {
                        val message = (uiState as UiState.Error).message
                        Text(
                            text = message,
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun EventDetailContent(event: Event, onAddToCalendar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        event.imageUrl?.let { imageUrl ->
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = event.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = event.name, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = event.description, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Дата: ${event.date}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Время: ${event.time}", style = MaterialTheme.typography.bodySmall)
        Text(
            text = "Местоположение: (${event.location.latitude}, ${event.location.longitude})",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomButtonOne(
            onClick = { onAddToCalendar() },
            text = "Добавить в календарь",
            iconResId = Icons.Default.CalendarMonth
        )
    }
}

fun addToCalendar(context: android.content.Context, event: Event) {
    ToastExt.show("Ивент добавлен в личный календарь")
}