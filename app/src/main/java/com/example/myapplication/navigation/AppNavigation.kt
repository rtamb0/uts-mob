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
import com.example.myapplication.ui.add.Add
import com.example.myapplication.ui.add.AddRoute
import com.example.myapplication.ui.detail.Detail
import com.example.myapplication.ui.detail.DetailScreen
import com.example.myapplication.ui.edit.Edit
import com.example.myapplication.ui.edit.EditRoute
import com.example.myapplication.ui.home.Home
import com.example.myapplication.ui.home.HomeScreen
import com.example.myapplication.ui.viewmodel.BarangViewModel

@Composable
fun AppNavigation(compactView : Boolean) {
    val navController = rememberNavController()

    val application = LocalContext.current.applicationContext as BarangApplication

    val barangViewModel = viewModel {
        BarangViewModel(application.barangRepository)
    }

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            val barang by barangViewModel.barang.collectAsStateWithLifecycle()

            HomeScreen(
                compactView,
                barang,
                onTersediaChange = { updatedBarang ->
                    barangViewModel.updateBarang(
                        updatedBarang
                    )
                },
                onOpenDetail = { id ->
                    navController.navigate(Detail(id = id))
                },
                onAdd = {
                    navController.navigate(Add)
                }
            )
        }
        composable<Add> {
            AddRoute(
                onBack = {
                    navController.popBackStack()
                },
                onSave = { newBarang ->
                    if (newBarang == null) return@AddRoute
                    barangViewModel.addBarang(newBarang)
                }
            )
        }
        composable<Detail> { entry ->
            val route = entry.toRoute<Detail>()
            val barang by barangViewModel.barang.collectAsStateWithLifecycle()
            val selectedBarang = barang.find { it.id == route.id }

            if (selectedBarang != null) {
                DetailScreen(
                    selectedBarang,
                    { navController.popBackStack() },
                    { navController.navigate(Edit(id = selectedBarang.id)) }
                )
            } else {
                Text("Item not found")
            }
        }
        composable<Edit> { entry ->
            val route = entry.toRoute<Edit>()
            val barang by barangViewModel.barang.collectAsStateWithLifecycle()
            val selectedBarang = barang.find { it.id == route.id }

            if (selectedBarang != null) {
                EditRoute(
                    barang = selectedBarang,
                    onBack = {
                        navController.popBackStack()
                    },
                    onSave = { updatedBarang ->
                        if (updatedBarang == null) return@EditRoute
                        barangViewModel.updateBarang(updatedBarang)
                    },
                    onDelete = {
                        barangViewModel.removeBarang(selectedBarang)
                        navController.navigate(Home) {
                            popUpTo<Home> {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                )
            } else {
                Text("Item not found")
            }
        }
    }
}
