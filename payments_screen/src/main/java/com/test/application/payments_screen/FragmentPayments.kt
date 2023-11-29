package com.test.application.payments_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.application.core.domain.Payment
import com.test.application.core.navigation.Navigator
import com.test.application.core.utils.AppState
import com.test.application.core.utils.ServerError
import com.test.application.core.utils.ServerException
import com.test.application.payments_screen.databinding.FragmentPaymentsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentPayments : Fragment() {
    private var _binding: FragmentPaymentsBinding? = null
    private val binding get() = _binding!!

    private val model: PaymentsViewModel by viewModel()
    private lateinit var paymentAdapter: PaymentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = arguments?.getString("token")
        if(token == null) {
            showError(ServerException(ServerError.TokenMissing))
            return
        }
        initViewModel(token)
        initRecyclerView()
        initLogoutButton()
    }

    private fun initLogoutButton() {
        binding.logoutButton.setOnClickListener {
            (activity as? Navigator)?.navigateFromPaymentsToLoginFragment()
        }
    }

    private fun initRecyclerView() {
        val recyclerView = binding.paymentsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        paymentAdapter = PaymentsAdapter(emptyList())
        recyclerView.adapter = paymentAdapter
    }

    private fun initViewModel(token: String) {
        model.getPayments(token)

        model.paymentsState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is AppState.Loading -> {
                    showLoading()
                }
                is AppState.Success -> {
                    hideLoading()
                    setupData(state.data)
                }
                is AppState.Error -> {
                    hideLoading()
                    showError(state.message)
                }
            }
        }
    }

    private fun setupData(data: List<Payment>) {

    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showError(error: Throwable) {
        hideLoading()
        val message = when(error) {
            is ServerException -> {
                when(error.error) {
                    ServerError.BodyNull -> getString(com.test.application.core.R.string.error_body_null)
                    ServerError.LoginFailed -> { getString(com.test.application.core.R.string.error_login_failed) }
                    ServerError.TokenMissing -> {getString(com.test.application.core.R.string.token_is_required)}
                    is ServerError.UnknownError -> getString(com.test.application.core.R.string.error_unknown)
                }
            }
            else -> {
                getString(com.test.application.core.R.string.error_generic)
            }
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}