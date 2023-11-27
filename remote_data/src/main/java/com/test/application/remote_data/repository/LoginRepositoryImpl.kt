package com.test.application.remote_data.repository

import com.test.application.core.domain.LoginResponse
import com.test.application.core.repository.LoginRepository
import com.test.application.core.utils.LoginError
import com.test.application.core.utils.LoginException
import com.test.application.remote_data.api.ApiService
import com.test.application.remote_data.utils.toDomain

class LoginRepositoryImpl(
    private val apiService: ApiService
) : LoginRepository {
    override suspend fun loginUser(login: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.loginUser(mapOf("login" to login, "password" to password))
            if(response.isSuccessful) {
                val body = response.body()
                if (body != null){
                    Result.success(body.toDomain())
                } else {
                    Result.failure(LoginException(LoginError.BodyNull))
                }
            } else {
                Result.failure(LoginException(LoginError.LoginFailed))
            }
        } catch (e: Exception) {
            Result.failure(LoginException(LoginError.UnknownError(e)))
        }
    }
}