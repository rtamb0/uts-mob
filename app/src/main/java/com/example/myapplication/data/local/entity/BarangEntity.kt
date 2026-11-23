package com.example.myapplication.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "barang")
data class BarangEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var namaBarang: String,
    var lokasiSimpan: String,
    var keterangan: String? = null,
    var tersedia: Boolean
)