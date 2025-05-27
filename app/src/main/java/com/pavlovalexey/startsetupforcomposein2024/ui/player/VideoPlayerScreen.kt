package com.pavlovalexey.startsetupforcomposein2024.ui.player

import android.media.MediaPlayer
import android.view.View
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeMute
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.pavlovalexey.startsetupforcomposein2024.R
import com.pavlovalexey.startsetupforcomposein2024.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(
    videoUrl: String,
    onBack: () -> Unit,
) {
    val context = LocalContext.current

    val httpDataSourceFactory = DefaultHttpDataSource.Factory()
        .setAllowCrossProtocolRedirects(true)
    val mediaSourceFactory = DefaultMediaSourceFactory(httpDataSourceFactory)
    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(mediaSourceFactory)
            .build().apply {
                setMediaItem(MediaItem.fromUri(videoUrl))
                playWhenReady = true
                prepare()
                volume = 1f
            }
    }

    var localMp by remember { mutableStateOf<MediaPlayer?>(null) }
    var isLocalPlaying by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        val mp = MediaPlayer.create(context, R.raw.geroi_3_main_menu).apply {
            isLooping = true
            setOnPreparedListener { it.start(); isLocalPlaying = true }
            setOnCompletionListener { isLocalPlaying = false }
        }
        localMp = mp
    }

    var isVideoAudioOn by remember { mutableStateOf(true) }
    LaunchedEffect(isVideoAudioOn) { exoPlayer.volume = if (isVideoAudioOn) 1f else 0f }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
            localMp?.release()
        }
    }

    BackHandler {
        localMp?.pause()
        exoPlayer.playWhenReady = false
        onBack()
    }

    var playbackSpeed by remember { mutableStateOf(1f) }
    val speeds = listOf(0.5f, 1f, 1.5f, 2f)
    var qualityIndex by remember { mutableStateOf(0) }
    val qualities = listOf("Auto", "720p", "480p")

    LaunchedEffect(playbackSpeed) {
        exoPlayer.playbackParameters = PlaybackParameters(playbackSpeed)
    }

    var isControllerVisible by remember { mutableStateOf(false) }

    Scaffold(
        contentWindowInsets = WindowInsets.systemBars
            .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        player = exoPlayer
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                        findViewById<PlayerControlView>(
                            com.google.android.exoplayer2.ui.R.id.exo_controller
                        )?.addVisibilityListener(object : PlayerControlView.VisibilityListener {
                            override fun onVisibilityChange(visibility: Int) {
                                isControllerVisible = visibility == View.VISIBLE
                            }
                        })
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            FloatingActionButton(
                onClick = {
                    localMp?.let {
                        if (isLocalPlaying) { it.pause(); isLocalPlaying = false }
                        else { it.start(); isLocalPlaying = true }
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = if (isLocalPlaying) Icons.Filled.MusicOff else Icons.Filled.MusicNote,
                    contentDescription = "Toggle local music",
                    tint = Color.Black
                )
            }

            FloatingActionButton(
                onClick = { isVideoAudioOn = !isVideoAudioOn },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(dp16)
            ) {
                Icon(
                    imageVector = if (isVideoAudioOn) Icons.Filled.VolumeUp else Icons.Filled.VolumeMute,
                    contentDescription = "Toggle video audio",
                    tint = Color.Black
                )
            }

            val bottomPadding = if (isControllerVisible) dp80 else dp16
            FloatingActionButton(
                onClick = {
                    playbackSpeed = speeds[(speeds.indexOf(playbackSpeed) + 1) % speeds.size]
                },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = dp16, bottom = bottomPadding)
            ) {
                Text(text = "${playbackSpeed}×", color = Color.Black)
            }

            FloatingActionButton(
                onClick = { qualityIndex = (qualityIndex + 1) % qualities.size },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = dp16, bottom = bottomPadding)
            ) {
                Text(text = qualities[qualityIndex], color = Color.Black)
            }
        }
    }
}