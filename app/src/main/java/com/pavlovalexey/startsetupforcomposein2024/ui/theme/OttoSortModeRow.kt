package com.pavlovalexey.startsetupforcomposein2024.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex

@Composable
fun OttoSortModeRow(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isPopular by remember { mutableStateOf(true) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dp16, vertical = dp4)
            .zIndex(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = if (isPopular) "По популярности" else "По алфавиту",
            modifier = Modifier
                .clickable {
                    isPopular = !isPopular
                    onClick()
                }
                .padding(vertical = dp8)
        )
    }
}