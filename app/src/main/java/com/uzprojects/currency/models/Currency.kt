package com.uzprojects.currency.models

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "Currency_table", primaryKeys = ["id", "Rate", "Date"])
data class Currency(
    val Ccy: String,
    val CcyNm_EN: String,
    val CcyNm_RU: String,
    val CcyNm_UZ: String,
    val CcyNm_UZC: String,
    val Code: String,
    val Date: String,
    val Diff: String,
    val Nominal: String,
    val Rate: String,
    val id: Int
) : Serializable