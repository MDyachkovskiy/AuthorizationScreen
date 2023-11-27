package com.test.application.core.utils

sealed class LoginError {
    object BodyNull : LoginError()
    object LoginFailed : LoginError()
    data class UnknownError(val exception: Exception) : LoginError()
}
