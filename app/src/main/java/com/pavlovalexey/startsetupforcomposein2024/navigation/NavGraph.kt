package com.pavlovalexey.startsetupforcomposein2024.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pavlovalexey.startsetupforcomposein2024.ui.eventdetail.EventDetailScreen
import com.pavlovalexey.startsetupforcomposein2024.ui.eventlist.EventListScreen
import com.pavlovalexey.startsetupforcomposein2024.viewmodel.EventDetailViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    onCloseApp: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "event_list",
        modifier = modifier
    ) {
        composable("event_list") {
            EventListScreen(
                viewModel = hiltViewModel(),
                onItemClick = { eventId ->
                    navController.navigate("event_detail/$eventId")
                },
                onCancel = onCloseApp
            )
        }

        composable(
            route = "event_detail/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel = hiltViewModel<EventDetailViewModel>(backStackEntry)
            val eventId = backStackEntry.arguments?.getString("eventId")
            if (eventId != null) {
                EventDetailScreen(viewModel = viewModel, navController = navController)
            } else {
                Text(
                    text = "Идентификатор события не предоставлен",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}