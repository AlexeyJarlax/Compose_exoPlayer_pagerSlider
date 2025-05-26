package com.pavlovalexey.startsetupforcomposein2024.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pavlovalexey.startsetupforcomposein2024.ui.WorkoutListScreen
import com.pavlovalexey.startsetupforcomposein2024.ui.eventdetail.WorkoutDetailScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "workout_list",
        modifier = modifier
    ) {
        composable("workout_list") {
            WorkoutListScreen( onItemClick = { id ->
                navController.navigate("workout_detail/$id")
            })
        }
        composable(
            route = "workout_detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStack ->
            val id = backStack.arguments!!.getInt("id")
            WorkoutDetailScreen(
                workoutId   = id,
                navController = navController
            )
        }
    }
}