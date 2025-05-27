package com.pavlovalexey.startsetupforcomposein2024.di

import android.content.Context
import androidx.room.Room
import com.pavlovalexey.startsetupforcomposein2024.model.WorkoutDao
import com.pavlovalexey.startsetupforcomposein2024.model.WorkoutDatabase
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
    fun provideWorkoutDatabase(
        @ApplicationContext context: Context
    ): WorkoutDatabase {
        return Room.databaseBuilder(
            context,
            WorkoutDatabase::class.java,
            "workouts_db"
        ).build()
    }

    @Provides
    fun provideWorkoutDao(db: WorkoutDatabase): WorkoutDao {
        return db.workoutDao()
    }
}