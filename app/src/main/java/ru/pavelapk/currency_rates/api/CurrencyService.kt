package ru.pavelapk.currency_rates.api

import ru.pavelapk.currency_rates.api.Utils.responseHandler
import ru.pavelapk.currency_rates.utils.Network

class CurrencyService {
    private val api: ICurrency = Network.retrofit.create(ICurrency::class.java)

    suspend fun daily() = responseHandler { api.getDaily() }
}