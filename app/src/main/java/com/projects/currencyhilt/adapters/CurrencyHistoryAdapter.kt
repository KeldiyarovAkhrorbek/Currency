package com.projects.currencyhilt.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projects.currencyhilt.databinding.ItemHistoryBinding
import com.projects.currencyhilt.models.Currency

class CurrencyHistoryAdapter :
    ListAdapter<Currency, CurrencyHistoryAdapter.Vh>(MyDiffUtil()) {
    inner class Vh(var itemHistoryBinding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(itemHistoryBinding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(currency: Currency, position: Int) {
            itemHistoryBinding.apply {
                val buyPrice = currency.Rate.toDouble()
                val diff = currency.Diff.toDouble()
                val sellPrice = buyPrice + diff
                sell.text = String.format("%.2f", sellPrice) + " UZS"
                buy.text = String.format("%.2f", buyPrice) + " UZS"
                textDate.text = currency.Date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position), position)
    }

    class MyDiffUtil : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.Code == newItem.Code
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.Code == newItem.Code
        }
    }

}



