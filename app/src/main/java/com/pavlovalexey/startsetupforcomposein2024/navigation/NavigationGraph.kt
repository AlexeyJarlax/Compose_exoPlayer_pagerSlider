package com.pavlovalexey.startsetupforcomposein2024.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.pavlovalexey.startsetupforcomposein2024.ui.bbb.DashboardScreen
import com.pavlovalexey.startsetupforcomposein2024.ui.aaa.HomeScreen
import com.pavlovalexey.startsetupforcomposein2024.ui.ccc.NotificationsScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.A.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(BottomNavItem.A.route) {
            HomeScreen()
        }
        composable(BottomNavItem.B.route) {
            DashboardScreen()
        }
        composable(BottomNavItem.C.route) {
            NotificationsScreen()
        }
    }
}