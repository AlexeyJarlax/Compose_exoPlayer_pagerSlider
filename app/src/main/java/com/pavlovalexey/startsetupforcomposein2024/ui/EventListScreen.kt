package com.pavlovalexey.startsetupforcomposein2024.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavlovalexey.startsetupforcomposein2024.ui.eventlist.EventListContent
import com.pavlovalexey.startsetupforcomposein2024.ui.eventlist.FilterSection
import com.pavlovalexey.startsetupforcomposein2024.ui.eventlist.SliderPagerIndicator
import com.pavlovalexey.startsetupforcomposein2024.viewmodel.EventListViewModel
import com.pavlovalexey.startsetupforcomposein2024.utils.loadImageUrlsFromAssets

@Composable
fun EventListScreen(
    onItemClick: (String) -> Unit,
    onVideoClick: (videoUrl: String) -> Unit,
    onCancel: () -> Unit,
    viewModel: EventListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val events by viewModel.filteredEvents.collectAsState()
    val showFilterDialog = remember { mutableStateOf(false) }
    var imageUrls by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(Unit) {
        imageUrls = loadImageUrlsFromAssets(context)
    }

    Scaffold(
        topBar = {
            FilterSection(viewModel = viewModel, onShowFilter = showFilterDialog)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showFilterDialog.value = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = "Фильтры"
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                SliderPagerIndicator(imageUrls = imageUrls){
                    onVideoClick(
                        "https://video-previews.elements.envatousercontent.com/decb66fe-0ba9-40e2-88bc-fb6d5a21d878/watermarked_preview/watermarked_preview.mp4",
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                EventListContent(
                    uiState = uiState,
                    events = events,
                    context = context,
                    onItemClick = onItemClick
                )
            }
        }
    )
}