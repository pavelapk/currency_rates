package ru.pavelapk.currency_rates.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyResponse(
    @SerialName("Date")
    val date: String,

    @SerialName("PreviousDate")
    val previousDate: String,

    @SerialName("Valute")
    val currencies: Map<String, CurrencyResponse>


)
