package com.projects.currencyhilt.resources

import com.projects.currencyhilt.models.Currency

sealed class CurrencyResource {
    object Loading : CurrencyResource()
    data class Success(val list: List<Currency>?) : CurrencyResource()
    data class Error(val message: String) : CurrencyResource()
}