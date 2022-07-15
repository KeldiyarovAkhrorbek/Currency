package com.uzprojects.currency.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzprojects.currency.models.Currency
import com.uzprojects.currency.networkhelper.NetworkHelper
import com.uzprojects.currency.repositories.CurrencyRepository
import com.uzprojects.currency.resources.CurrencyResource
import com.uzprojects.currency.resources.DatabaseResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    fun getCurrencies(): StateFlow<CurrencyResource> {
        val stateFlow = MutableStateFlow<CurrencyResource>(CurrencyResource.Loading)
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                val flow = currencyRepository.getCurrencies()
                flow.catch {
                    stateFlow.emit(CurrencyResource.Error(it.message ?: ""))
                }.collect {
                    if (it.isSuccessful) {
                        stateFlow.emit(CurrencyResource.Success(it.body()))
                        currencyRepository.addCurrency(it.body() ?: emptyList())
                    } else {
                        stateFlow.emit(CurrencyResource.Error(it.message() ?: ""))
                    }
                }
            } else {
                val flow = currencyRepository.getLatestCurrencies()
                flow.catch {
                    stateFlow.emit(CurrencyResource.Error(it.message ?: ""))
                }.collect {
                    stateFlow.emit(CurrencyResource.Success(it))
                }
            }
        }
        return stateFlow
    }

    fun addCurrency(list: List<Currency>) {
        viewModelScope.launch {
            currencyRepository.addCurrency(list)
        }
    }

    fun getSpecificCurrency(ccy: String): StateFlow<DatabaseResource> {
        val stateFlow = MutableStateFlow<DatabaseResource>(DatabaseResource.Loading)
        viewModelScope.launch {
            val flow = currencyRepository.getSpecificCurrency(ccy)
            flow.catch {
                stateFlow.emit(DatabaseResource.Error(it.message ?: ""))
            }.collect {
                stateFlow.emit(DatabaseResource.Success(it))
            }
        }

        return stateFlow
    }

    fun justAddCurrencies() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                val flow = currencyRepository.getCurrencies()
                flow.catch {

                }.collect {
                    if (it.isSuccessful) {
                        currencyRepository.addCurrency(it.body() ?: emptyList())
                    }
                }
            }
        }
    }

}