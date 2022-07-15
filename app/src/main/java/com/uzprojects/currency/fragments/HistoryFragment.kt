package com.uzprojects.currency.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.uzprojects.currency.adapters.CurrencyHistoryAdapter
import com.uzprojects.currency.databinding.FragmentHistoryBinding
import com.uzprojects.currency.resources.DatabaseResource
import com.uzprojects.currency.viewmodels.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var currencyHistoryAdapter: CurrencyHistoryAdapter
    private val viewModel: CurrencyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        currencyHistoryAdapter = CurrencyHistoryAdapter()
        binding.rv.adapter = currencyHistoryAdapter
        lifecycleScope.launch {
            viewModel.getSpecificCurrency(param1!!).collect {
                when (it) {
                    is DatabaseResource.Loading -> {

                    }

                    is DatabaseResource.Success -> {
                        currencyHistoryAdapter.submitList(it.list)
                    }

                    is DatabaseResource.Error -> {

                    }
                }
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}