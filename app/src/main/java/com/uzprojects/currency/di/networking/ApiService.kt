package com.uzprojects.currency.di.networking

import com.uzprojects.currency.models.Currency
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("json")
    suspend fun getData(): Response<List<Currency>>
}