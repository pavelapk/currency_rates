package ru.pavelapk.currency_rates

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.pavelapk.currency_rates.data.CurrencyRepository

class CurrenciesViewModel(app: Application) : AndroidViewModel(app) {
    private val currencyRepository = CurrencyRepository(app)

    val currencies = currencyRepository.observeAll().asLiveData()

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    init {
        refreshCurrencies()
    }

    fun refreshCurrencies() {
        viewModelScope.launch {
            _isRefreshing.value = true
            currencyRepository.loadCurrenciesFromApi()
            _isRefreshing.value = false
        }
    }

}