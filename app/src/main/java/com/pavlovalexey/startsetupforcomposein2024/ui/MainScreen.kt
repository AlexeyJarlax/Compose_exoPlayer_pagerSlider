package com.pavlovalexey.startsetupforcomposein2024.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.pavlovalexey.startsetupforcomposein2024.navigation.NavGraph
import androidx.navigation.NavHostController
import com.pavlovalexey.startsetupforcomposein2024.utils.LocationPermissionHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, onCloseApp: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            NavGraph(
                navController = navController,
                onCloseApp = onCloseApp,
                modifier = Modifier.padding(innerPadding)
            )
        }
    )

    LocationPermissionHandler {
    }
}