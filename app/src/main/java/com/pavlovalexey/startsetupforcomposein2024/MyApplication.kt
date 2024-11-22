    package com.pavlovalexey.startsetupforcomposein2024

    import android.app.Application
    import com.pavlovalexey.startsetupforcomposein2024.utils.ToastExt
    import dagger.hilt.android.HiltAndroidApp

    @HiltAndroidApp
    class MyApplication : Application() {
        override fun onCreate() {
            super.onCreate()
            ToastExt.init(this)
        }
    }