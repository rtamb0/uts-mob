package com.example.myapplication.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.local.entity.BarangEntity
import com.example.myapplication.ui.components.BarangCard
import kotlinx.serialization.Serializable

@Serializable
object Home

@Composable
fun HomeScreen(
    barangList: List<BarangEntity>,
    onTersediaChange: (BarangEntity) -> Unit,
    onOpenDetail: (Int) -> Unit,
    onAdd: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = onAdd) {
            Text("Add")
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                items(barangList.size) { index ->
                    BarangCard(
                        barangList[index],
                        onTersediaChange = {
                            onTersediaChange(
                                barangList[index].copy(tersedia = it)
                            )
                        },
                        onOpenDetail = onOpenDetail
                    )
                }
            }
        )
    }
}
