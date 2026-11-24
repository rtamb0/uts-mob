package com.example.myapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.BarangApplication
import com.example.myapplication.navigation.AppNavigation
import com.example.myapplication.ui.components.AppHeader
import com.example.myapplication.ui.viewmodel.SettingsViewModel

@Composable
fun App() {
    val application = LocalContext.current.applicationContext as BarangApplication

    val settingsViewModel = viewModel {
        SettingsViewModel(application.settingsDataStore)
    }

    val compactView by settingsViewModel.compactView.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            AppHeader(compactView, { settingsViewModel.setCompactView(it) })
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    innerPadding
                )
        ) {
            AppNavigation(compactView)
        }
    }
}
