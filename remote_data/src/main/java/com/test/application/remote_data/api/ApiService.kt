package com.test.application.remote_data.api

import com.test.application.remote_data.dto.login.LoginRequest
import com.test.application.remote_data.dto.login.LoginResponseDTO
import com.test.application.remote_data.dto.payments.PaymentsResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun loginUser(
        @Body userData: LoginRequest
    ): Response<LoginResponseDTO>

    @GET("payments")
    suspend fun getPayments(
        @Header("token") token: String
    ): Response<PaymentsResponseDTO>
}