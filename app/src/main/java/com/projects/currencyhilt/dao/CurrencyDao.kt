package com.projects.currencyhilt.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.projects.currencyhilt.models.Currency

@Dao
interface CurrencyDao {

    @Insert(onConflict = REPLACE)
    suspend fun addCurrency(list: List<Currency>)

    @Query("select * from Currency_table ORDER BY Date DESC limit 75")
    suspend fun getLatestCurrencies(): List<Currency>

    @Query("select * from Currency_table where Ccy = :ccy ORDER BY Date DESC")
    suspend fun getSpecificCurrency(ccy: String): List<Currency>
}