package ru.pavelapk.currency_rates

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.pavelapk.currency_rates.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel by viewModels<CurrenciesViewModel>()

    private val currencyAdapter = CurrencyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.recycler.adapter = currencyAdapter
        viewModel.currencies.observe(this) {
            currencyAdapter.submitList(it)
        }

        viewModel.isRefreshing.observe(this) {
            binding.swipeRefresh.isRefreshing = it
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshCurrencies()
        }
    }

}