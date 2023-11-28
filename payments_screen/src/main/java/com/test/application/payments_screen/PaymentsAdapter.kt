package com.test.application.payments_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.application.core.domain.Payment
import com.test.application.payments_screen.databinding.ItemPaymentBinding

class PaymentsAdapter(
    private val payments: List<Payment>
) : RecyclerView.Adapter<PaymentsAdapter.PaymentViewHolder>() {
    inner class PaymentViewHolder(
        private val binding: ItemPaymentBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(payment: Payment){
            with(binding) {
                tvPaymentAmount.text = payment.amount.toString()
                tvPaymentTitle.text = payment.title
                tvTransactionTime.text = payment.created.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val binding = ItemPaymentBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentViewHolder(binding)
    }

    override fun getItemCount() = payments.size

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment = payments[position]
        holder.bind(payment)
    }
}