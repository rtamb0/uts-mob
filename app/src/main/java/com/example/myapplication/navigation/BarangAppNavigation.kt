package com.example.myapplication.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myapplication.BarangApplication
import com.example.myapplication.ui.add.BarangAddRoute
import com.example.myapplication.ui.detail.BarangDetailScreen
import com.example.myapplication.ui.home.BarangHome
import com.example.myapplication.ui.detail.BarangDetail
import com.example.myapplication.ui.edit.BarangEditRoute
import com.example.myapplication.ui.edit.BarangEdit
import com.example.myapplication.ui.home.BarangHomeScreen
import com.example.myapplication.ui.viewmodel.BarangViewModel
import com.example.myapplication.ui.add.BarangAdd

@Composable
fun BarangAppNavigation() {
    val navController = rememberNavController()

    val application = LocalContext.current.applicationContext as BarangApplication

    val barangViewModel = viewModel {
        BarangViewModel(application.barangRepository)
    }

    NavHost(
        navController = navController,
        startDestination = BarangHome
    ) {
        composable<BarangHome> {
            val barang by barangViewModel.barang.collectAsStateWithLifecycle()

            BarangHomeScreen(
                barang,
                onTersediaChange = { updatedBarang -> barangViewModel.updateBarang(
                    updatedBarang
                ) },
                onOpenDetail = { id ->
                    navController.navigate(BarangDetail(id = id))
                },
                onAdd = {
                    navController.navigate(BarangAdd)
                }
            )
        }
        composable<BarangAdd> {
            BarangAddRoute(
                onBack = {
                    navController.popBackStack()
                },
                onSave = { newBarang ->
                    if (newBarang == null) return@BarangAddRoute
                    barangViewModel.addBarang(newBarang)
                }
            )
        }
        composable<BarangDetail> {  entry ->
            val route = entry.toRoute<BarangDetail>()
            val barang by barangViewModel.barang.collectAsStateWithLifecycle()
            val selectedBarang = barang.find { it.id == route.id }

            if (selectedBarang != null) {
                BarangDetailScreen(selectedBarang, { navController.popBackStack() }, { navController.navigate(BarangEdit(id = selectedBarang.id)) })
            } else {
                Text("Item not found")
            }
        }
        composable<BarangEdit> {  entry ->
            val route = entry.toRoute<BarangEdit>()
            val barang by barangViewModel.barang.collectAsStateWithLifecycle()
            val selectedBarang = barang.find { it.id == route.id }

            if (selectedBarang != null) {
                BarangEditRoute(
                    barang = selectedBarang,
                    onBack = {
                        navController.popBackStack()
                    },
                    onSave = { updatedBarang ->
                        if (updatedBarang == null) return@BarangEditRoute
                        barangViewModel.updateBarang(updatedBarang)
                    }
                )
            } else {
                Text("Item not found")
            }
        }
    }
}