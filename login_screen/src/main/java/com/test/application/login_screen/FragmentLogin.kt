package com.test.application.login_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.test.application.core.domain.LoginResponse
import com.test.application.core.utils.AppState
import com.test.application.core.utils.LoginError
import com.test.application.core.utils.LoginException
import com.test.application.login_screen.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentLogin : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val model: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initEditText()
    }

    private fun initEditText() {
        with(binding) {
            exitButton.setOnClickListener {
                val login = etLogin.text.toString()
                val password = etPassword.text.toString()
                model.loginUser(login, password)
            }
        }

    }

    private fun initViewModel() {
        model.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AppState.Loading -> {
                    showLoading()
                }
                is AppState.Success -> {
                    navigateToPaymentsScreen(state.data)
                }
                is AppState.Error -> {
                    showError(state.message)
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        setInteraction(false)
    }

    private fun setInteraction(enabled: Boolean) {
        binding.exitButton.isEnabled = enabled
        binding.etLogin.isEnabled = enabled
        binding.etPassword.isEnabled = enabled
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        setInteraction(true)
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun navigateToPaymentsScreen(loginResponse: LoginResponse) {

    }

    private fun showError(error: Throwable) {
        hideLoading()
        val message = when(error) {
            is LoginException -> {
                when(error.error) {
                    LoginError.BodyNull -> getString(com.test.application.core.R.string.error_body_null)
                    LoginError.LoginFailed -> getString(com.test.application.core.R.string.error_login_failed)
                    is LoginError.UnknownError -> getString(com.test.application.core.R.string.error_unknown)
                }
            }
            else -> {
                getString(com.test.application.core.R.string.error_generic)
            }
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}