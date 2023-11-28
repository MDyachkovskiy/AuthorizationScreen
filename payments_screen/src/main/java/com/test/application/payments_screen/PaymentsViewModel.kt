package com.test.application.payments_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.application.core.domain.Payment
import com.test.application.core.repository.PaymentsRepository
import com.test.application.core.utils.AppState
import kotlinx.coroutines.launch

class PaymentsViewModel(
    private val repository: PaymentsRepository
) : ViewModel() {

    private val _paymentsState = MutableLiveData<AppState<List<Payment>>>()
    val paymentsState: LiveData<AppState<List<Payment>>> = _paymentsState

    fun getPayments(token: String) {
        viewModelScope.launch {
            _paymentsState.value = AppState.Loading
            try {
                val result = repository.getPayments(token)
                result.fold(
                    onSuccess = { payments ->
                        _paymentsState.value = AppState.Success(payments)
                    },
                    onFailure = { exception ->
                        _paymentsState.value = AppState.Error(exception)
                    }
                )
            } catch (e: Exception) {
                _paymentsState.value = AppState.Error(e)
            }
        }
    }
}