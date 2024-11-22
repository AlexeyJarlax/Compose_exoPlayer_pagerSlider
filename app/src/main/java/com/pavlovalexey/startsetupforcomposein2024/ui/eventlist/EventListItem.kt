package com.pavlovalexey.startsetupforcomposein2024.ui.eventlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.pavlovalexey.startsetupforcomposein2024.model.Event

@Composable
fun EventListItem(event: Event, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        event.imageUrl?.let {
            Image(
                painter = rememberImagePainter(data = it),
                contentDescription = event.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Crop
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = event.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${event.type} • ${event.date}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${"%.1f".format(event.distance)} км",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}