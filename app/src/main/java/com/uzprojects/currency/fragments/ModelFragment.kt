package com.uzprojects.currency.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uzprojects.currency.databinding.FragmentModelBinding
import com.uzprojects.currency.models.Currency

private const val ARG_PARAM1 = "param1"

class ModelFragment : Fragment() {
    private var param1: Currency? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Currency
        }
    }

    private var _binding: FragmentModelBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentModelBinding.inflate(inflater, container, false)
        val simpleDate = param1?.Date
        binding.apply {
            dateTv.text = simpleDate
            val price = param1?.Rate?.toDouble() as? Double
            val diff = param1?.Diff?.toDouble() as? Double
            val sell = price!! + diff!!
            sellPriceTv.text = "${String.format("%.2f", price)} UZS"
            buyPriceTv.text = "${String.format("%.2f", sell)} UZS"
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Currency) =
            ModelFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}