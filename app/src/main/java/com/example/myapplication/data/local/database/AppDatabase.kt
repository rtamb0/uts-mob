
package com.example.myapplication.data.local.database

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.example.myapplication.data.local.dao.BarangDao
import com.example.myapplication.data.local.dao.PlaceDao
import com.example.myapplication.data.local.entity.PlaceEntity
import com.example.myapplication.data.local.entity.BarangEntity

@Database(
    entities = [BarangEntity::class, PlaceEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao
    abstract fun barangDao(): BarangDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database"
                )
                    .setDriver(AndroidSQLiteDriver())
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
