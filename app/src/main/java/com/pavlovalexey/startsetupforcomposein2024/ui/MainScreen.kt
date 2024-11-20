package com.pavlovalexey.startsetupforcomposein2024.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.pavlovalexey.startsetupforcomposein2024.navigation.NavigationGraph
import com.pavlovalexey.startsetupforcomposein2024.ui.components.BottomNavigationContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("точка входа") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            sheetState.show()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Меню"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            NavigationGraph(
                navController = navController,
                paddingValues = innerPadding
            )
        }
    )

    if (sheetState.isVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch { sheetState.hide() }
            },
            sheetState = sheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            BottomNavigationContent(navController = navController) {
                scope.launch {
                    sheetState.hide()
                }
            }
        }
    }
}