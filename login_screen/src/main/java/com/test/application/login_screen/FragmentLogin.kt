package com.test.application.login_screen

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.os.bundleOf
import com.google.android.material.textfield.TextInputLayout
import com.test.application.core.domain.LoginResponse
import com.test.application.core.navigation.Navigator
import com.test.application.core.utils.ServerError
import com.test.application.core.utils.ServerException
import com.test.application.core.view.BaseFragment
import com.test.application.login_screen.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentLogin : BaseFragment<LoginResponse, FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {

    private val model: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initLoginButton()
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

    private fun initLoginButton() {
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
            renderData(state)
        }
    }

    private fun setInteraction(enabled: Boolean) {
        binding.exitButton.isEnabled = enabled
        binding.etLogin.isEnabled = enabled
        binding.etPassword.isEnabled = enabled
    }

    override fun findProgressBar(): FrameLayout {
        return binding.progressBar
    }

    override fun showViewLoading() {
        binding.progressBar.visibility = View.VISIBLE
        setInteraction(false)
    }

    override fun setupData(data: LoginResponse) {
        val token = data.response.token
        navigateToPaymentsScreen(token)
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        setInteraction(true)
    }

    private fun navigateToPaymentsScreen(token: String) {
        val bundle = bundleOf("token" to token)
        (activity as? Navigator)?.navigateToPaymentsFragment(bundle)
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

    override fun showErrorDialog(error: Throwable) {
        super.showErrorDialog(error)
        if(error is ServerException && error.error == ServerError.LoginFailed) {
            setFieldErrorColor(binding.etLayoutLogin)
            setFieldErrorColor(binding.etLayoutPassword)
        }
    }
}