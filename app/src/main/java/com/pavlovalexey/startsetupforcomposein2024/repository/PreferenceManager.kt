package com.pavlovalexey.startsetupforcomposein2024.repository

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PreferenceManager @Inject constructor(
    private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = "user_preferences")

    companion object {
        val LAST_UPDATE_TIME = longPreferencesKey("last_update_time")
    }

    val lastUpdateTime: Flow<Long?> = context.dataStore.data
        .map { preferences ->
            preferences[LAST_UPDATE_TIME]
        }

    suspend fun setLastUpdateTime(time: Long) {
        context.dataStore.edit { preferences ->
            preferences[LAST_UPDATE_TIME] = time
        }
    }
}