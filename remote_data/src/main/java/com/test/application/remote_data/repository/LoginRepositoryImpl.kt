package com.test.application.remote_data.repository

import com.test.application.core.domain.LoginResponse
import com.test.application.core.repository.LoginRepository
import com.test.application.core.utils.ServerError
import com.test.application.core.utils.ServerException
import com.test.application.remote_data.api.ApiService
import com.test.application.remote_data.dto.login.LoginRequest
import com.test.application.remote_data.utils.toDomain

class LoginRepositoryImpl(
    private val apiService: ApiService
) : LoginRepository {
    override suspend fun loginUser(login: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.loginUser(LoginRequest(login, password))
            if(response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success == "true"){
                    Result.success(body.toDomain())
                } else {
                    Result.failure(ServerException(ServerError.BodyNull))
                }
            } else {
                Result.failure(ServerException(ServerError.LoginFailed))
            }
        } catch (e: Exception) {
            Result.failure(ServerException(ServerError.UnknownError(e)))
        }
    }
}