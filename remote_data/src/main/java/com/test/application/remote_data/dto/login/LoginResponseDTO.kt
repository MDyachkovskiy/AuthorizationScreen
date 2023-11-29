package com.test.application.remote_data.dto.login

import com.google.gson.annotations.SerializedName

data class LoginResponseDTO(
    @SerializedName("response")
    val tokenResponse: TokenResponseDTO = TokenResponseDTO(),
    val success: String = ""
)