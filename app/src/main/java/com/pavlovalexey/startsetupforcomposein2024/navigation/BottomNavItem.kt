package com.pavlovalexey.startsetupforcomposein2024.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object A : BottomNavItem("aaa", Icons.Filled.Home, "A")
    object B : BottomNavItem("bbb", Icons.Filled.Dashboard, "B")
    object C : BottomNavItem("ccc", Icons.Filled.Notifications, "C")
}