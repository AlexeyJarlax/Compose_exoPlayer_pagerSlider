package com.pavlovalexey.startsetupforcomposein2024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.pavlovalexey.startsetupforcomposein2024.ui.MainScreen
import com.pavlovalexey.startsetupforcomposein2024.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppTheme {
                MainScreen(
                    navController = navController,
                    onCloseApp = { finish() }
                )
            }
        }
    }
}