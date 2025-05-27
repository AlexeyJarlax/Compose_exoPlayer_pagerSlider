package com.pavlovalexey.startsetupforcomposein2024.ui.theme

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun AlexClearButton(
    onClear: () -> Unit,
    verticalOffset: Dp = dp0
) {
    IconButton(
        onClick = { onClear() },
        modifier = Modifier
            .padding(start = dp0, end = dp12)
            .offset(y = verticalOffset)
            .size(dp16)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Очистить",
            modifier = Modifier.size(dp16)
        )
    }
}