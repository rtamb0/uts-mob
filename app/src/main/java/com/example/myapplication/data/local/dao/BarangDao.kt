package com.example.myapplication.data.local.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.example.myapplication.data.local.entity.BarangEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BarangDao {
    @Query("SELECT * FROM barang")
    fun observeAll(): Flow<List<BarangEntity>>

    @Query("SELECT COUNT(*) FROM barang")
    suspend fun count(): Int

    @Insert
    suspend fun insert(item: BarangEntity)

    @Insert
    suspend fun insertAll(items: List<BarangEntity>)

    @Update
    suspend fun update(item: BarangEntity)

    @Delete
    suspend fun delete(item: BarangEntity)
}
