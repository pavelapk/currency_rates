package ru.pavelapk.currency_rates.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "currency")
data class CurrencyEntity(
    @PrimaryKey val id: String,
    val charCode: String,
    val nominal: Int,
    val name: String,
    val value: Double,
    val previous: Double
) {
    fun toDomain() = Currency(
        id,
        charCode,
        nominal,
        name,
        value,
        previous,
        BigDecimal(value) - BigDecimal(previous)
    )
}