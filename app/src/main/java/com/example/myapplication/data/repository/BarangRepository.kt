package com.example.myapplication.data.repository

import com.example.myapplication.data.local.dao.BarangDao
import com.example.myapplication.data.local.entity.BarangEntity
import com.example.myapplication.data.local.source.BarangData.initialBarang
import kotlinx.coroutines.flow.Flow

class BarangRepository(private val dao: BarangDao) {
    val barang: Flow<List<BarangEntity>> = dao.observeAll()

    suspend fun add(item: BarangEntity) = dao.insert(item)

    suspend fun remove(item: BarangEntity) = dao.delete(item)

    suspend fun update(item: BarangEntity) = dao.update(item)

    suspend fun seedIfEmpty() {
        if (dao.count() == 0) {
            dao.insertAll(initialBarang)
        }
    }
}
