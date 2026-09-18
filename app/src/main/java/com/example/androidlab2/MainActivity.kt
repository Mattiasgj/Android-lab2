package com.example.androidlab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidlab2.view.NormalScreen
import com.example.androidlab2.view.SecurityAlertScreen
import com.example.androidlab2.viewmodel.MainViewModel
import com.example.androidlab2.viewmodel.SecurityUiState

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        SecurityUiState.Normal -> {
            NormalScreen()
        }

        is SecurityUiState.SecurityAlert -> {
            SecurityAlertScreen(
                confidenceScore = state.confidenceScore,
                rawComment = state.rawComment
            )
        }
    }
}
