package com.example.myapplication.ui.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.local.entity.BarangEntity

@Composable
fun EditRoute(barang: BarangEntity, onBack: () -> Unit, onSave: (BarangEntity?) -> Unit, onDelete: ()-> Unit) {
    val viewModel = viewModel {
        EditViewModel(barang)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    EditScreen(
        barang = barang,
        uiState = uiState,
        onNamaBarangChange = viewModel::onNamaBarangChange,
        onLokasiSimpanChange = viewModel::onLokasiSimpanChange,
        onKeteranganChange = viewModel::onKeteranganChange,
        onTersediaChange = viewModel::onTersediaChange,
        onBack = onBack,
        onSave = {
            viewModel.onSave().let(onSave)
        },
        onDelete = onDelete
    )
}
