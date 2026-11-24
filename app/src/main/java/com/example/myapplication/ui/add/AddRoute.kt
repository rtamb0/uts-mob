package com.example.myapplication.ui.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.local.entity.BarangEntity

@Composable
fun AddRoute(onBack: () -> Unit, onSave: (BarangEntity?) -> Unit) {
    val barang = BarangEntity(
        namaBarang = "",
        lokasiSimpan = "",
        keterangan = "",
        tersedia = true
    )

    val viewModel = viewModel {
        AddViewModel(barang)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AddScreen(
        barang = barang,
        uiState = uiState,
        onNamaBarangChange = viewModel::onNamaBarangChange,
        onLokasiSimpanChange = viewModel::onLokasiSimpanChange,
        onKeteranganChange = viewModel::onKeteranganChange,
        onTersediaChange = viewModel::onTersediaChange,
        onBack = onBack,
        onSave = {
            viewModel.onSave().let(onSave)
        }
    )
}
