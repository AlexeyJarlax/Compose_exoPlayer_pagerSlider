package com.pavlovalexey.startsetupforcomposein2024.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pavlovalexey.startsetupforcomposein2024.navigation.BottomNavItem

@Composable
fun BottomNavigationContent(
    navController: NavHostController,
    onItemClick: () -> Unit
) {
    val items = listOf(
        BottomNavItem.A,
        BottomNavItem.B,
        BottomNavItem.C
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            Button(
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    onItemClick()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = item.title)
            }
        }
    }
}