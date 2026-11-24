package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.entity.BarangEntity
import com.example.myapplication.data.repository.BarangRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BarangViewModel(
    private val repository: BarangRepository
) : ViewModel() {

    val barang = repository.barang
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            repository.seedIfEmpty()
        }
    }

    fun addBarang(barang: BarangEntity) {
        viewModelScope.launch {
            repository.add(barang)
        }
    }

    fun removeBarang(barang: BarangEntity) {
        viewModelScope.launch {
            repository.remove(barang)
        }
    }

    fun updateBarang(barang: BarangEntity) {
        viewModelScope.launch {
            repository.update(barang)
        }
    }
}