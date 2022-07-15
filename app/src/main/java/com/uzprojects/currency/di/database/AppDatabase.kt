package com.uzprojects.currency.di.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uzprojects.currency.dao.CurrencyDao
import com.uzprojects.currency.models.Currency

@Database(
    entities = [Currency::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
}