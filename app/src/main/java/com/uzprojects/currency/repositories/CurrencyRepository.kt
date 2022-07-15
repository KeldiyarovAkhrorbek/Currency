package com.uzprojects.currency.repositories

import com.uzprojects.currency.dao.CurrencyDao
import com.uzprojects.currency.di.networking.ApiService
import com.uzprojects.currency.models.Currency
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