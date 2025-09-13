package com.allenth17.casestudy2.screen

sealed class UiState<out T> {

    data object Loading : UiState<Nothing>()

    data object Idle : UiState<Nothing>()

    data class Success<out T>(
        val data: T
    ) : UiState<T>()

    data class Error(
        val message: String
    ) : UiState<Nothing>()
}