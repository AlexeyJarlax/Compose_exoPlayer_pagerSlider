package com.pavlovalexey.startsetupforcomposein2024.utils

import android.Manifest
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionHandler(onPermissionGranted: () -> Unit) {
    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(Unit) {
        locationPermissionState.launchPermissionRequest()
    }

    when {
        locationPermissionState.status.isGranted -> {
            onPermissionGranted()
        }
        locationPermissionState.status.shouldShowRationale -> {
            AlertDialog(
                onDismissRequest = { },
                title = { Text("Разрешение на местоположение") },
                text = { Text("Приложению необходимо разрешение на доступ к вашему местоположению для отображения ближайших событий.") },
                confirmButton = {
                    TextButton(onClick = { locationPermissionState.launchPermissionRequest() }) {
                        Text("Разрешить")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { }) {
                        Text("Отмена")
                    }
                }
            )
        }
        else -> {
        }
    }
}