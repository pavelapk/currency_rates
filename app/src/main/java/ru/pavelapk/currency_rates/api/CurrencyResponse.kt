package ru.pavelapk.currency_rates.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.pavelapk.currency_rates.data.Currency
import ru.pavelapk.currency_rates.data.CurrencyEntity
import java.math.BigDecimal

@Serializable
data class CurrencyResponse(
    @SerialName("ID")
    val id: String,

    @SerialName("CharCode")
    val charCode: String,

    @SerialName("Nominal")
    val nominal: Int,

    @SerialName("Name")
    val name: String,

    @SerialName("Value")
    val value: Double,

    @SerialName("Previous")
    val previous: Double
) {
    fun toEntity() = CurrencyEntity(id, charCode, nominal, name, value, previous)
}

