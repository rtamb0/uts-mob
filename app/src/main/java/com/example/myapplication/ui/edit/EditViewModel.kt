package com.example.myapplication.ui.edit

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.local.entity.BarangEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditViewModel(
    private val barang: BarangEntity
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        EditUiState(
            namaBarang = barang.namaBarang,
            lokasiSimpan = barang.lokasiSimpan,
            keterangan = barang.keterangan,
            tersedia = barang.tersedia
        )
    )

    val uiState = _uiState.asStateFlow()

    fun onNamaBarangChange(value: String) {
        _uiState.update { it.copy(namaBarang = value) }
    }

    fun onLokasiSimpanChange(value: String) {
        _uiState.update { it.copy(lokasiSimpan = value) }
    }

    fun onKeteranganChange(value: String) {
        _uiState.update { it.copy(keterangan = value) }
    }

    fun onTersediaChange(value: Boolean) {
        _uiState.update { it.copy(tersedia = value) }
    }

    fun onSave(): BarangEntity? {
        if (!_uiState.value.isFormValid) return null
        barang.namaBarang = _uiState.value.namaBarang
        barang.lokasiSimpan = _uiState.value.lokasiSimpan
        barang.keterangan = _uiState.value.keterangan
        barang.tersedia = _uiState.value.tersedia

        return barang
    }
}
