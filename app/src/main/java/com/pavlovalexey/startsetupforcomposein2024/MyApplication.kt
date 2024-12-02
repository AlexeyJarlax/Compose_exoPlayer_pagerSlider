    package com.pavlovalexey.startsetupforcomposein2024

    import android.app.Application
    import com.pavlovalexey.startsetupforcomposein2024.utils.ToastExt
    import dagger.hilt.android.HiltAndroidApp
    import timber.log.Timber

    @HiltAndroidApp
    class MyApplication : Application() {
        override fun onCreate() {
            super.onCreate()
            ToastExt.init(this)
//            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
//            } else {
//                Timber.plant(ReleaseTree())
//            }
        }
    }