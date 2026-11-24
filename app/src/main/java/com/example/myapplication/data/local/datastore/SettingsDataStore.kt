package com.example.myapplication.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "settings"
)

class SettingsDataStore(
    private val context: Context
) {
    companion object {
        private val COMPACT_VIEW = booleanPreferencesKey("compact_view")
    }

    val compactView: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[COMPACT_VIEW] ?: false
        }

    suspend fun setCompactView(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[COMPACT_VIEW] = enabled
        }
    }
}