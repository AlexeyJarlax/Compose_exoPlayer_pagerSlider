package com.pavlovalexey.startsetupforcomposein2024.di

import com.pavlovalexey.startsetupforcomposein2024.repository.EventRepository
import com.pavlovalexey.startsetupforcomposein2024.repository.EventRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindEventRepository(
        eventRepositoryImpl: EventRepositoryImpl
    ): EventRepository
}