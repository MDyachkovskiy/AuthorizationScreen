package com.test.application.core.repository

import com.test.application.core.domain.Payment

interface PaymentsRepository {
    suspend fun getPayments(token: String): Result<List<Payment>>
}