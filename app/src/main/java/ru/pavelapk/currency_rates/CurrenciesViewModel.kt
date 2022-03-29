package ru.pavelapk.currency_rates

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.pavelapk.currency_rates.data.Currency
import ru.pavelapk.currency_rates.data.CurrencyRepository
import java.math.BigDecimal
import java.math.RoundingMode

class CurrenciesViewModel(app: Application) : AndroidViewModel(app) {
    private val currencyRepository = CurrencyRepository(app)

    val currencies = currencyRepository.observeAll().asLiveData()

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    private val _convertOutput = MutableLiveData<BigDecimal>()
    val convertOutput: LiveData<BigDecimal> get() = _convertOutput

    private var selectedCurrency: Currency? = null

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        refreshCurrencies()
        viewModelScope.launch {
            while (true) {
                refreshCurrencies(showAnimation = false)
                delay(30000)
            }
        }
    }

    fun selectCurrency(currencyId: String) {
        selectedCurrency = currencies.value?.find { it.id == currencyId }
    }

    fun refreshCurrencies(showAnimation: Boolean = true) = viewModelScope.launch {
        if (showAnimation) _isRefreshing.value = true

        currencyRepository.loadCurrenciesFromApi(
            onFailure = {
                _errorMessage.value = it.localizedMessage
            }
        )

        _isRefreshing.value = false
    }


    fun convert(inputStr: String) {
        val input = inputStr.toBigDecimalOrNull() ?: return
        selectedCurrency?.let {
            val v = it.value
            _convertOutput.value = (input * BigDecimal(it.nominal))
                .divide(BigDecimal(v), 3, RoundingMode.HALF_UP)
        }
    }

}