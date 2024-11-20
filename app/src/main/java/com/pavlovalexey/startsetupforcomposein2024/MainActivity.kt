package com.pavlovalexey.startsetupforcomposein2024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pavlovalexey.startsetupforcomposein2024.ui.MainScreen
import com.pavlovalexey.startsetupforcomposein2024.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }
}