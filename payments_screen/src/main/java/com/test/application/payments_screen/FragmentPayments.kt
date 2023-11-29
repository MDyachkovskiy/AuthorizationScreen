package com.test.application.payments_screen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.application.core.domain.Payment
import com.test.application.core.navigation.Navigator
import com.test.application.core.utils.ServerError
import com.test.application.core.utils.ServerException
import com.test.application.core.view.BaseFragment
import com.test.application.payments_screen.databinding.FragmentPaymentsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentPayments : BaseFragment<List<Payment>, FragmentPaymentsBinding>(
    FragmentPaymentsBinding::inflate
) {

    private val model: PaymentsViewModel by viewModel()
    private lateinit var paymentAdapter: PaymentsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = arguments?.getString("token")
        if(token == null) {
            showErrorDialog(ServerException(ServerError.TokenMissing))
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
        paymentAdapter = PaymentsAdapter()
        recyclerView.adapter = paymentAdapter
    }

    private fun initViewModel(token: String) {
        model.getPayments(token)
        model.paymentsState.observe(viewLifecycleOwner) { state ->
            renderData(state)
        }
    }

    override fun findProgressBar(): FrameLayout {
        return binding.progressBar
    }

    override fun showViewLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.animate()
            .alpha(0.0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    progressBar.visibility = View.GONE
                }
            })
    }

    override fun setupData(data: List<Payment>) {
        paymentAdapter.updateData(data)
    }
}