package com.uzprojects.currency.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.uzprojects.currency.databinding.ItemSpinnerBinding
import com.uzprojects.currency.models.Currency
import com.squareup.picasso.Picasso


class SpinnerAdapter(private val context: Context, private val list: ArrayList<Currency>) :
    BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(p0: Int): Currency = list[p0]

    override fun getItemId(p0: Int): Long = list.size.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val binding = ItemSpinnerBinding.inflate(LayoutInflater.from(context), p2, false)
        binding.apply {
            Picasso.get()
                .load("https://countryflagsapi.com/png/" + list[p0].Ccy[0] + list[p0].Ccy[1])
                .into(flagImage)
            text.text = list[p0].Ccy
        }
        return binding.root
    }
}