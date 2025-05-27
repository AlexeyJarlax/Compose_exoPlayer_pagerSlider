package com.pavlovalexey.startsetupforcomposein2024.di

import com.pavlovalexey.startsetupforcomposein2024.repository.WorkoutRepository
import com.pavlovalexey.startsetupforcomposein2024.repository.WorkoutRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkoutRepositoryModule {

    @Binds
    abstract fun bindWorkoutRepository(
        impl: WorkoutRepositoryImpl
    ): WorkoutRepository
}