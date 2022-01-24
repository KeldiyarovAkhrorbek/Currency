package com.projects.currencyhilt.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.projects.currencyhilt.fragments.HistoryFragment
import com.projects.currencyhilt.models.Currency

class ViewPagerHistoryAdapter(var list: List<Currency>, fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return HistoryFragment.newInstance(list[position].Ccy)
    }
}