package com.test.application.core.utils

class LoginException(
    val error: LoginError
) : Exception()