package com.pavlovalexey.startsetupforcomposein2024.utils

import android.view.Window
import androidx.core.view.WindowCompat

fun setStatusBarColor(window: Window, color: Int) {
    @Suppress("DEPRECATION")
    window.statusBarColor = color
    window.decorView.setBackgroundColor(color)
    WindowCompat.setDecorFitsSystemWindows(window, false)
}