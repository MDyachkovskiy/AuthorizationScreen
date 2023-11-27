package com.test.application.core.domain

data class LoginResponse(
    val success : String,
    val response: TokenResponse
)