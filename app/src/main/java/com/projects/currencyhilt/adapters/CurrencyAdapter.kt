package com.projects.currencyhilt.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projects.currencyhilt.databinding.ItemCurrency2Binding
import com.projects.currencyhilt.models.Currency
import com.squareup.picasso.Picasso

class CurrencyAdapter(var onItemClickListener: OnItemClickListener) :
    ListAdapter<Currency, CurrencyAdapter.Vh>(MyDiffUtil()) {
    inner class Vh(var itemCurrencyBinding: ItemCurrency2Binding) :
        RecyclerView.ViewHolder(itemCurrencyBinding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(currency: Currency, position: Int) {
            itemCurrencyBinding.apply {
                Picasso.get()
                    .load("https://countryflagsapi.com/png/" + currency.Ccy[0] + currency.Ccy[1])
                    .into(flagImage)
                textCode.text = currency.Ccy
                val price = currency.Rate.toDouble()
                val diff = currency.Diff.toDouble()
                buyTv.text = "$price UZS"
                val sell = price + diff
                val sellST = String.format("%.2f", sell)
                sellTv.text = "$sellST UZS"
            }

            itemCurrencyBinding.layout.setOnClickListener {
                onItemClickListener.onItemClick(currency)
            }

            itemCurrencyBinding.calculator.setOnClickListener {
                onItemClickListener.onItemCalculatorClick(currency, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemCurrency2Binding.inflate(
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

    interface OnItemClickListener {
        fun onItemCalculatorClick(currency: Currency, position: Int)
        fun onItemClick(currency: Currency)
    }
}



