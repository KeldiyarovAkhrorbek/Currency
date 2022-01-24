package com.projects.currencyhilt.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.projects.currencyhilt.MainActivity
import com.projects.currencyhilt.R
import com.projects.currencyhilt.adapters.SpinnerAdapter
import com.projects.currencyhilt.databinding.FragmentCalculatorBinding
import com.projects.currencyhilt.models.Currency
import com.projects.currencyhilt.resources.CurrencyResource
import com.projects.currencyhilt.viewmodels.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CurrencyViewModel by viewModels()
    private lateinit var spinnerAdapter: SpinnerAdapter
    private var list = ArrayList<Currency>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        spinnerAdapter = SpinnerAdapter(requireContext(), list)
        binding.apply {
            spFrom.adapter = spinnerAdapter
            spTo.adapter = spinnerAdapter
        }
        lifecycleScope.launch {
            viewModel.getCurrencies().collect {
                when (it) {
                    is CurrencyResource.Loading -> {

                    }

                    is CurrencyResource.Success -> {
                        list.addAll(it.list ?: emptyList())
                        val date = Calendar.getInstance().time
                        val simpleDate = SimpleDateFormat("dd.MM.yyyy")
                        val simDate = simpleDate.format(date)
                        list.add(
                            Currency(
                                "UZS",
                                "Uzbek SUM",
                                "Узбекский сум",
                                "O\'zbek so\'mi",
                                "Узбек суми",
                                "102",
                                simDate,
                                "0",
                                "1",
                                "1",
                                600
                            )
                        )
                        spinnerAdapter.notifyDataSetChanged()

                        binding.apply {
                            download.visibility = View.GONE
                            layout.visibility = View.VISIBLE
                        }
                    }

                    is CurrencyResource.Error -> {

                    }
                }
            }
        }

        if (arguments != null) {
            val pos = arguments?.getInt("pos") as Int
            if (pos == 0) {
                binding.spTo.setSelection(1)
            } else
                binding.spFrom.setSelection(pos)
        } else {
            binding.spTo.setSelection(1)
        }

        binding.apply {
            spFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                @SuppressLint("SetTextI18n")
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val from = list[p2]
                    val to = list[spTo.selectedItemPosition]
                    var n = edit.text.toString()
                    if (n.isEmpty()) {
                        n = "0"
                    }
                    val price =
                        from.Rate.toFloat() / to.Rate.toFloat() * n
                            .toFloat()
                    buyPriceTv.text = "%.2f".format(price) + " " + to.Ccy
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
            spTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                @SuppressLint("SetTextI18n")
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val to = list[p2]
                    val from = list[spFrom.selectedItemPosition]
                    var n = edit.text.toString()
                    if (n.isEmpty()) {
                        n = "0"
                    }
                    val price =
                        from.Rate.toFloat() / to.Rate.toFloat() * n.toFloat()

                    buyPriceTv.text = "%.2f".format(price) + " " + to.Ccy
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
            edit.addTextChangedListener {
                val from = list[spFrom.selectedItemPosition]
                val to = list[spTo.selectedItemPosition]
                var n = edit.text.toString()
                if (n.isEmpty()) {
                    n = "0"
                }
                val price =
                    from.Rate.toFloat() / to.Rate.toFloat() * n.toFloat()

                buyPriceTv.text = "%.2f".format(price) + " " + to.Ccy
            }
            switchBtn.setOnClickListener {
                val fromPos = spFrom.selectedItemPosition
                val toPos = spTo.selectedItemPosition
                spTo.setSelection(fromPos, true)
                spFrom.setSelection(toPos, true)
                val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_rotate)
                imgSwitch.startAnimation(anim)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }
}