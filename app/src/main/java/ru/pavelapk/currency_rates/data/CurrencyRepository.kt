package ru.pavelapk.currency_rates.data

import android.content.Context
import kotlinx.coroutines.flow.map
import ru.pavelapk.currency_rates.api.CurrencyService
import ru.pavelapk.currency_rates.utils.Database

class CurrencyRepository(context: Context) {
    private val db = Database.getInstance(context)
    private val currencyService = CurrencyService()
    private val currencyDao = db.currencyDao()

    fun observeAll() = currencyDao.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    suspend fun loadCurrenciesFromApi() {
        val dailyResponse = currencyService.daily().getOrElse { return }
        val currencies = dailyResponse.currencies.values.map {
            it.toEntity()
        }
        currencyDao.insertAllCurrencies(currencies)
    }

}