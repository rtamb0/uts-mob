package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.local.database.AppDatabase
import com.example.myapplication.data.repository.BarangRepository

class BarangApplication : Application() {
    val database by lazy {
        AppDatabase.getDatabase(this)
    }

    val barangRepository by lazy {
        BarangRepository(database.barangDao())
    }
}