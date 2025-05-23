package com.pavlovalexey.startsetupforcomposein2024.ui.eventlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.pavlovalexey.startsetupforcomposein2024.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderPagerIndicator(
    imageUrls: List<String>,
    onVideoClick: () -> Unit,
) {
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(244.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (imageUrls.isNotEmpty()) {
                HorizontalPager(
                    count = imageUrls.size,
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    val painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(imageUrls[page])
                            .error(R.drawable.ic_dashboard_black_24dp)
                            .fallback(R.drawable.ic_dashboard_black_24dp)
                            .build()
                    )
                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }

            IconButton(
                onClick = onVideoClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .size(56.dp)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Videocam,
                    contentDescription = "Воспроизвести видео",
                    tint = Color.White
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        )
    }
}