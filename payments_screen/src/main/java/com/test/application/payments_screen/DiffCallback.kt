package com.test.application.payments_screen

import androidx.recyclerview.widget.DiffUtil
import com.test.application.core.domain.Payment

class DiffCallback(
    private val oldList: List<Payment>,
    private val newList: List<Payment>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}