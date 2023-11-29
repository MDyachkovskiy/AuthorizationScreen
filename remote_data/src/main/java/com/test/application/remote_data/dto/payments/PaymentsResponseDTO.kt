package com.test.application.remote_data.dto.payments

import com.google.gson.annotations.SerializedName

data class PaymentsResponseDTO(
    @SerializedName("response")
    val payments: List<PaymentDTO> = listOf(),
    val success: String = ""
)