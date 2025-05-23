package com.pavlovalexey.startsetupforcomposein2024.ui.eventlist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.pavlovalexey.startsetupforcomposein2024.viewmodel.EventListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSection(
    viewModel: EventListViewModel,
    onShowFilter: MutableState<Boolean>
) {
    if (onShowFilter.value) {
        FilterDialog(
            onDismiss = { onShowFilter.value = false },
            onApply = { type, date, distance ->
                viewModel.setFilterType(type)
                viewModel.setFilterDate(date)
                viewModel.setFilterDistance(distance)
                onShowFilter.value = false
            }
        )
    }

    TopAppBar(
        title = { Text("События рядом") },
        actions = {
            IconButton(onClick = { onShowFilter.value = true }) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = "Фильтры"
                )
            }
        }
    )
}