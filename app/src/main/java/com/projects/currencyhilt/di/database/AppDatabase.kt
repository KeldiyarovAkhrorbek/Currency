package com.projects.currencyhilt.di.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.projects.currencyhilt.dao.CurrencyDao
import com.projects.currencyhilt.models.Currency

@Database(
    entities = [Currency::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
}