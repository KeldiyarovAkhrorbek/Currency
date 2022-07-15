package com.uzprojects.currency.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.HoverMode
import com.anychart.enums.TooltipPositionMode
import com.uzprojects.currency.MainActivity
import com.uzprojects.currency.R
import com.uzprojects.currency.adapters.CurrencyAdapter
import com.uzprojects.currency.databinding.FragmentAllCurrenciesBinding
import com.uzprojects.currency.databinding.ItemChartBinding
import com.uzprojects.currency.models.Currency
import com.uzprojects.currency.resources.CurrencyResource
import com.uzprojects.currency.resources.DatabaseResource
import com.uzprojects.currency.viewmodels.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AllCurrenciesFragment : Fragment() {

    private var _binding: FragmentAllCurrenciesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CurrencyViewModel by viewModels()
    private lateinit var adapter: CurrencyAdapter
    private val TAG = "AllCurrenciesFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllCurrenciesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        adapter = CurrencyAdapter(object : CurrencyAdapter.OnItemClickListener {
            override fun onItemCalculatorClick(currency: Currency, position: Int) {
                val bundle = Bundle()
                bundle.putInt("pos", position)
                findNavController().navigate(
                    R.id.action_allCurrenciesFragment_to_calculatorFragment,
                    bundle
                )
            }

            override fun onItemClick(currency: Currency) {
                val builder = AlertDialog.Builder(requireContext())
                val binding: ItemChartBinding =
                    ItemChartBinding.inflate(layoutInflater)
                builder.setView(binding.root)
                val alertDialog = builder.create()
                builder.setTitle("Rate change for ${currency.Ccy}")
                lifecycleScope.launch {
                    viewModel.getSpecificCurrency(currency.Ccy).collect {
                        when (it) {
                            is DatabaseResource.Success -> {
                                val chart = AnyChart.column()
                                val data: MutableList<DataEntry> = ArrayList()
                                val list = it.list
                                list?.forEach {
                                    data.add(ValueDataEntry(it.Date, it.Rate.toFloat()))
                                }

                                chart.tooltip().positionMode(TooltipPositionMode.POINT);
                                chart.interactivity().hoverMode(HoverMode.BY_X);

                                chart.data(data)
                                chart.animation(true)

                                binding.barchart.setChart(chart)
                            }

                        }
                    }
                }

                builder.setPositiveButton("OK", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        alertDialog.dismiss()
                    }
                })
                builder.setCancelable(false)
                builder.show()

            }
        })

        binding.rv.adapter = adapter

        lifecycleScope.launch {
            viewModel.getCurrencies().collect {
                when (it) {
                    is CurrencyResource.Loading -> {
                        binding.rv.visibility = View.GONE
                        binding.download.visibility = View.VISIBLE
                    }

                    is CurrencyResource.Success -> {
                        binding.rv.visibility = View.VISIBLE
                        binding.download.visibility = View.GONE
                        adapter.submitList(it.list)
                    }

                    is CurrencyResource.Error -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }





        return binding.root
    }


    override fun onResume() {
        super.onResume()
        (activity as MainActivity).provideIndicator()
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).provideIndicator()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}