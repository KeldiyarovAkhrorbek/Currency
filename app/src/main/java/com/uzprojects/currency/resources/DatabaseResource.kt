package com.uzprojects.currency.resources

import com.uzprojects.currency.models.Currency


sealed class DatabaseResource {
    object Loading : DatabaseResource()
    data class Success(val list: List<Currency>?) : DatabaseResource()
    data class Error(val message: String) : DatabaseResource()
}