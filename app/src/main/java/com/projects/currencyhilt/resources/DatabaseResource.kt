package com.projects.currencyhilt.resources

import com.projects.currencyhilt.models.Currency


sealed class DatabaseResource {
    object Loading : DatabaseResource()
    data class Success(val list: List<Currency>?) : DatabaseResource()
    data class Error(val message: String) : DatabaseResource()
}