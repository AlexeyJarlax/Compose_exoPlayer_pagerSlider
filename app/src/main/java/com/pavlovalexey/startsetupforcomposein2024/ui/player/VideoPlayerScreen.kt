package com.pavlovalexey.startsetupforcomposein2024.ui.player

import com.pavlovalexey.startsetupforcomposein2024.R
import android.media.MediaPlayer
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(
    videoUrl: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            playWhenReady = true
            prepare()
        }
    }

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isAudioPlaying by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val mp = MediaPlayer.create(context, R.raw.geroi_3_main_menu).apply {
            isLooping = true
            setOnPreparedListener {
                it.start()
                isAudioPlaying = true
            }
            setOnCompletionListener {
                isAudioPlaying = false
            }
        }
        mediaPlayer = mp
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
            mediaPlayer?.release()
        }
    }

    BackHandler {
        mediaPlayer?.pause()
        exoPlayer.playWhenReady = false
        onBack()
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        player = exoPlayer
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            LargeFloatingActionButton(
                onClick = {
                    mediaPlayer?.let { mp ->
                        if (isAudioPlaying) {
                            mp.pause()
                            isAudioPlaying = false
                        } else {
                            mp.start()
                            isAudioPlaying = true
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = if (isAudioPlaying) Icons.Filled.MusicOff else Icons.Filled.MusicNote,
                    contentDescription = if (isAudioPlaying) "Пауза аудио" else "Воспроизвести аудио"
                )
            }
        }
    }
}