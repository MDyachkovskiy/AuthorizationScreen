package com.test.application.login_screen

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.test.application.core.domain.LoginResponse
import com.test.application.core.utils.AppState
import com.test.application.core.utils.ServerError
import com.test.application.core.utils.ServerException
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
        setupTextChangeListeners()
    }

    private fun setupTextChangeListeners() {
        binding.etLogin.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                resetFieldColor(binding.etLayoutLogin)
                resetFieldColor(binding.etLayoutPassword)
            }
        }

        binding.etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                resetFieldColor(binding.etLayoutLogin)
                resetFieldColor(binding.etLayoutPassword)
            }
        }
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
                    hideLoading()
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
            is ServerException -> {
                when(error.error) {
                    ServerError.BodyNull -> getString(com.test.application.core.R.string.error_body_null)
                    ServerError.LoginFailed -> {
                        setFieldErrorColor(binding.etLayoutLogin)
                        setFieldErrorColor(binding.etLayoutPassword)
                        getString(com.test.application.core.R.string.error_login_failed)
                    }
                    is ServerError.UnknownError -> getString(com.test.application.core.R.string.error_unknown)
                }
            }
            else -> {
                getString(com.test.application.core.R.string.error_generic)
            }
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun setFieldErrorColor(textInputLayout: TextInputLayout) {
        val alertColor = ContextCompat
            .getColor(
                requireContext(),
                com.test.application.core.R.color.edit_text_alert
            )
        val backgroundColor = ColorUtils
            .setAlphaComponent(alertColor, 38)
        textInputLayout.boxBackgroundColor = backgroundColor
    }

    private fun resetFieldColor(textInputLayout: TextInputLayout) {
        textInputLayout.boxBackgroundColor = Color.TRANSPARENT
    }
}