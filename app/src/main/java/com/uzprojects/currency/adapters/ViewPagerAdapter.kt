package com.uzprojects.currency.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.uzprojects.currency.fragments.ModelFragment
import com.uzprojects.currency.models.Currency

class ViewPagerAdapter(var list: List<Currency>, fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return ModelFragment.newInstance(list[position])
    }
}