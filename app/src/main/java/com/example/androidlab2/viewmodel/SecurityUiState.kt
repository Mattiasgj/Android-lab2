package com.example.androidlab2.viewmodel

sealed interface SecurityUiState {

    data object Normal : SecurityUiState

    data class SecurityAlert(
        val confidenceScore: Int,
        val rawComment: String
    ) : SecurityUiState
}