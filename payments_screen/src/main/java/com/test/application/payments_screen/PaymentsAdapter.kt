package com.test.application.payments_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.application.core.domain.Payment
import com.test.application.payments_screen.databinding.ItemPaymentBinding

class PaymentsAdapter : RecyclerView.Adapter<PaymentsAdapter.PaymentViewHolder>() {

    private var payments: List<Payment> = listOf()
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

    fun updateData(newPayments: List<Payment>) {
        val diffCallback = DiffCallback(payments, newPayments)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        payments = newPayments
        diffResult.dispatchUpdatesTo(this)
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