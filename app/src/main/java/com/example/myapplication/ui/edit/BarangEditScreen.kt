package com.example.myapplication.ui.edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.local.entity.BarangEntity
import kotlinx.serialization.Serializable

@Serializable
data class BarangEdit(val id: Int)
@Composable
fun BarangEditScreen(
    barang: BarangEntity,
    uiState: BarangEditUiState,
    onNamaBarangChange: (String) -> Unit,
    onLokasiSimpanChange: (String) -> Unit,
    onKeteranganChange: (String) -> Unit,
    onTersediaChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    onSave: () -> Unit
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = onBack) {
            Text("Back")
        }
        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.namaBarang,
                    onValueChange = onNamaBarangChange,
                    label = { Text("Nama Barang") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = uiState.lokasiSimpan,
                    onValueChange = onLokasiSimpanChange,
                    label = { Text("Lokasi Simpan") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = uiState.keterangan,
                    onValueChange = onKeteranganChange,
                    label = { Text("Keterangan") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Tersedia?")
                    Checkbox(
                        barang.tersedia,
                        onTersediaChange,
                        modifier = Modifier.scale(0.8f)
                    )
                }
                Button(
                    onClick = {
                        onSave()
                        onBack()
                    },
                    enabled = uiState.isFormValid,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save")
                }
            }
        }
    }
}