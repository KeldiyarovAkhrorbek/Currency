package com.projects.currencyhilt.di.networking

import com.projects.currencyhilt.models.Currency
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("json")
    suspend fun getData(): Response<List<Currency>>
}