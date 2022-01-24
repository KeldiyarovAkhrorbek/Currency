package com.projects.currencyhilt.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.projects.currencyhilt.MainActivity
import com.projects.currencyhilt.adapters.ViewPagerAdapter
import com.projects.currencyhilt.adapters.ViewPagerHistoryAdapter
import com.projects.currencyhilt.databinding.FragmentHomeBinding
import com.projects.currencyhilt.resources.CurrencyResource
import com.projects.currencyhilt.viewmodels.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CurrencyViewModel by viewModels()
    private val TAG = "HomeFragment"
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPagerHistoryAdapter: ViewPagerHistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        lifecycleScope.launch {
            viewModel.getCurrencies().collect {
                when (it) {
                    is CurrencyResource.Loading -> {

                    }

                    is CurrencyResource.Success -> {
                        viewPagerAdapter =
                            ViewPagerAdapter(it.list ?: emptyList(), this@HomeFragment)
                        viewPagerHistoryAdapter =
                            ViewPagerHistoryAdapter(it.list ?: emptyList(), this@HomeFragment)
                        binding.viewpager.adapter = viewPagerAdapter
                        binding.viewpagerHistory.adapter = viewPagerHistoryAdapter
                        binding.springDotsIndicator.attachToPager(binding.viewpager)
                        binding.apply {
                            TabLayoutMediator(tabLayout, viewpager) { tab, position ->

                            }.attach()

                            TabLayoutMediator(tabLayout, viewpagerHistory) { tab, position ->
                                tab.text = it.list?.get(position)?.Ccy
                            }.attach()
                            binding.viewpager.setPageTransformer(object :
                                ViewPager2.PageTransformer {
                                private val MIN_SCALE = 0.65f
                                private val MIN_ALPHA = 0.3f

                                override fun transformPage(page: View, position: Float) {
                                    when {
                                        position < -1 -> {
                                            page.alpha = 0F
                                        }
                                        position <= 1 -> {
                                            page.scaleX = MIN_SCALE.coerceAtLeast(1 - abs(position))
                                            page.scaleY = MIN_SCALE.coerceAtLeast(1 - abs(position))
                                            page.alpha = MIN_ALPHA.coerceAtLeast(1 - abs(position))
                                        }
                                        else -> {
                                            page.alpha = 0F
                                        }
                                    }
                                }
                            })
                        }

                    }

                    is CurrencyResource.Error -> {

                    }

                }

            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).provideIndicator()
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).provideIndicator()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }
}