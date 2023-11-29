package com.test.application.core.utils

sealed class ServerError {
    object BodyNull : ServerError()
    object LoginFailed : ServerError()
    object FetchFailed: ServerError()
    object TokenMissing: ServerError()
    data class UnknownError(val exception: Exception) : ServerError()
}
