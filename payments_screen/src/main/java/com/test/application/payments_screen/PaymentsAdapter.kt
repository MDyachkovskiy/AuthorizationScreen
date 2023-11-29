package com.test.application.payments_screen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.application.core.domain.Payment
import com.test.application.payments_screen.databinding.ItemPaymentBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PaymentsAdapter(
    private val context: Context
) : RecyclerView.Adapter<PaymentsAdapter.PaymentViewHolder>() {

    private var payments: List<Payment> = listOf()
    inner class PaymentViewHolder(
        private val binding: ItemPaymentBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(payment: Payment){
            with(binding) {
                tvPaymentAmount.text = formatAmount(payment.amount)
                tvPaymentTitle.text = payment.title
                tvTransactionTime.text = formatTimestamp(payment.created)
            }
        }

        private fun formatTimestamp(time: Int?): String {
            if (time == null) {
                return context.getString(com.test.application.core.R.string.unknown_date)
            }
            val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
            val date = Date(time.toLong() * 1000)
            return formatter.format(date)
        }

        private fun formatAmount(amount: Double): String {
            val formatter = NumberFormat.getNumberInstance(Locale("ru", "RU"))
            return formatter.format(amount)
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