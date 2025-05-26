package com.pavlovalexey.startsetupforcomposein2024.utils

import android.R
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Keep
import kotlin.let

@Keep
object ToastExt {

    private lateinit var appContext: Context
    private val mainHandler = Handler(Looper.getMainLooper())

    fun init(application: Application) {
        appContext = application.applicationContext
    }

    fun show(message: String, duration: Int = Toast.LENGTH_LONG) {
        mainHandler.post {
            val toast = Toast.makeText(appContext, message, duration)
            val toastView = toast.view
            val toastMessage = toastView?.findViewById<TextView>(R.id.message)
            toastMessage?.let {
                it.setTextColor(Color.BLACK)
                it.setBackgroundColor(Color.WHITE)
            }
            toast.show()
        }
    }
}