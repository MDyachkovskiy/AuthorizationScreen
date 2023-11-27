package com.test.application.core.repository

import com.test.application.core.domain.LoginResponse

interface LoginRepository {
    suspend fun loginUser(login: String, password: String): Result<LoginResponse>

}