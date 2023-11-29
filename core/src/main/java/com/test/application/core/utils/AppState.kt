package com.test.application.core.utils

sealed class AppState<out T> {
    object Loading: AppState<Nothing>()
    data class Success<out T>(val data: T) : AppState<T>()
    data class Error(val message: Throwable) : AppState<Nothing>()
}