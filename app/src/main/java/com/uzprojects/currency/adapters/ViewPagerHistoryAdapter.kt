package com.uzprojects.currency.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.uzprojects.currency.fragments.HistoryFragment
import com.uzprojects.currency.models.Currency

class ViewPagerHistoryAdapter(var list: List<Currency>, fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return HistoryFragment.newInstance(list[position].Ccy)
    }
}