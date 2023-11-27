package com.test.application.remote_data.utils

import com.test.application.core.domain.LoginResponse
import com.test.application.core.domain.TokenResponse
import com.test.application.remote_data.dto.login.LoginResponseDTO
import com.test.application.remote_data.dto.login.TokenResponseDTO

fun LoginResponseDTO.toDomain() : LoginResponse{
    return LoginResponse(
        success = this.success,
        response = this.tokenResponse.toDomain()
    )
}

fun TokenResponseDTO.toDomain(): TokenResponse{
    return TokenResponse(
        token = this.token
    )
}

