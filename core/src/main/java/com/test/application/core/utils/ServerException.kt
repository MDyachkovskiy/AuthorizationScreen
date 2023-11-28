package com.test.application.core.utils

class ServerException(
    val error: ServerError
) : Exception()