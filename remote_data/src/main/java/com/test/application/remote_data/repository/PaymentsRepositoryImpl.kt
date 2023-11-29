package com.test.application.remote_data.repository

import com.test.application.core.domain.Payment
import com.test.application.core.repository.PaymentsRepository
import com.test.application.core.utils.ServerError
import com.test.application.core.utils.ServerException
import com.test.application.remote_data.api.ApiService
import com.test.application.remote_data.utils.toDomain

class PaymentsRepositoryImpl(
    private val apiService: ApiService
) : PaymentsRepository {
    override suspend fun getPayments(token: String): Result<List<Payment>> {
        return try {
            val response = apiService.getPayments(token)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success == "true") {
                    Result.success(body.payments.map { it.toDomain() })
                } else {
                    Result.failure(ServerException(ServerError.BodyNull))
                }
            } else {
                Result.failure(ServerException(ServerError.FetchFailed))
            }
        } catch (e: Exception) {
            Result.failure(ServerException(ServerError.UnknownError(e)))
        }
    }
}