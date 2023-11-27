package com.test.application.remote_data.api

import com.test.application.remote_data.dto.login.LoginResponseDTO
import com.test.application.remote_data.dto.payments.PaymentDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("app-key: 12345", "v: 1")
    @POST("/login")
    suspend fun loginUser(
        @Body userData: Map<String, String>
    ): Response<LoginResponseDTO>

    @Headers("app-key: 12345", "v: 1")
    @GET("/payments")
    suspend fun getPayments(
        @Header("token") token: String
    ): Response<List<PaymentDTO>>
}