package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.local.entity.BarangEntity

@Composable
fun BarangCard(barang: BarangEntity, onTersediaChange: (Boolean) -> Unit, onOpenDetail: (Int) -> Unit) {
    Card(onClick = { onOpenDetail(barang.id) }) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(barang.namaBarang, style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                Checkbox(barang.tersedia, onTersediaChange,  modifier = Modifier.scale(0.8f) )
            }
            Text(barang.lokasiSimpan, style = MaterialTheme.typography.bodySmall)
        }
    }
}
