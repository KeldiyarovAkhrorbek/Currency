package com.projects.currencyhilt.repositories

import com.projects.currencyhilt.dao.CurrencyDao
import com.projects.currencyhilt.di.networking.ApiService
import com.projects.currencyhilt.models.Currency
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val apiService: ApiService,
    private val currencyDao: CurrencyDao
) {
    suspend fun getCurrencies() = flow { emit(apiService.getData()) }
    suspend fun addCurrency(list: List<Currency>) = currencyDao.addCurrency(list)
    suspend fun getLatestCurrencies() = flow { emit(currencyDao.getLatestCurrencies()) }
    suspend fun getSpecificCurrency(ccy: String) =
        flow { emit(currencyDao.getSpecificCurrency(ccy)) }

}