package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.local.database.AppDatabase
import com.example.myapplication.data.repository.BarangRepository
import com.example.myapplication.data.local.datastore.SettingsDataStore

class BarangApplication : Application() {

    val settingsDataStore by lazy {
        SettingsDataStore(this)
    }

    val database by lazy {
        AppDatabase.getDatabase(this)
    }

    val barangRepository by lazy {
        BarangRepository(database.barangDao())
    }
}