package com.example.myapplication.ui.edit

data class BarangEditUiState(
    val namaBarang: String = "",
    val lokasiSimpan: String = "",
    val keterangan: String = "",
    val tersedia: Boolean = true
) {
    val isFormValid: Boolean
        get() = namaBarang.isNotBlank() &&
                lokasiSimpan.isNotBlank()
}