package com.test.application.remote_data.dto.payments

data class PaymentDTO(
    val id: Int = 0,
    val title: String = "",
    val amount: String? = null,
    val created: Int? = null
)