package com.example.myapplication.data.local.source

import com.example.myapplication.data.local.entity.BarangEntity

object BarangData {
    val initialBarang = listOf(
        BarangEntity(
            id = 1,
            namaBarang = "Box Grand Theft Auto VI",
            lokasiSimpan = "Rak Kamar",
            keterangan = "Box kaset tapi tidak ada kasetnya",
            tersedia = true
        ),
        BarangEntity(
            id = 2,
            namaBarang = "Nintendo Wii",
            lokasiSimpan = "Laci Kamar",
            keterangan = "Model pertama",
            tersedia = false
        ),
        BarangEntity(
            id = 3,
            namaBarang = "Air Conditioner",
            lokasiSimpan = "Dinding Kamar",
            keterangan = "AC yang dingin",
            tersedia = true
        ),
        BarangEntity(
            id = 4,
            namaBarang = "Elgato Capture Card",
            lokasiSimpan = "Laci Ruang Tamu",
            keterangan = "Hanya support 30fps",
            tersedia = false
        ),
    )
}