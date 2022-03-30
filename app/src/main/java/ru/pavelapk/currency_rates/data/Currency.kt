package ru.pavelapk.currency_rates.data

import java.math.BigDecimal

data class Currency(
    val id: String,
    val charCode: String,
    val nominal: Int,
    val name: String,
    val value: Double,
    val previous: Double,
    val diff: BigDecimal
)
