package com.test.application.login_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.application.core.domain.LoginResponse
import com.test.application.core.repository.LoginRepository
import com.test.application.core.utils.AppState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<AppState<LoginResponse>>()
    val loginState: LiveData<AppState<LoginResponse>> = _loginState

    fun loginUser(login: String, password: String) {
        viewModelScope.launch {
            _loginState.postValue(AppState.Loading)
            try {
                val result = repository.loginUser(login, password)
                result.fold(
                    onSuccess = {data -> _loginState.postValue(AppState.Success(data))},
                    onFailure = {exception ->
                        _loginState.postValue(AppState.Error(exception))}
                )
            } catch (e: Exception){
                _loginState.postValue(AppState.Error(e))
            }
        }
    }
}