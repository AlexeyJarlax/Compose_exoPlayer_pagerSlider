package com.pavlovalexey.startsetupforcomposein2024.di

import android.content.Context
import androidx.room.Room
import com.pavlovalexey.startsetupforcomposein2024.model.AppDatabase
import com.pavlovalexey.startsetupforcomposein2024.model.EventDao
import com.pavlovalexey.startsetupforcomposein2024.repository.PreferenceManager
import com.pavlovalexey.startsetupforcomposein2024.repository.LocationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@dagger.hilt.android.qualifiers.ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "events_db"
        ).build()
    }

    @Provides
    fun provideEventDao(appDatabase: AppDatabase): EventDao {
        return appDatabase.eventDao()
    }

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManager {
        return PreferenceManager(context)
    }
}


