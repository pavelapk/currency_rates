package ru.pavelapk.currency_rates

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.delay
import ru.pavelapk.currency_rates.data.CurrencyCode
import ru.pavelapk.currency_rates.databinding.ActivityMainBinding
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel by viewModels<CurrenciesViewModel>()

    private val currencyAdapter = CurrencyAdapter()
    private val spinnerAdapter by lazy {
        ArrayAdapter<CurrencyCode>(
            this,
            android.R.layout.simple_spinner_dropdown_item
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.recycler.adapter = currencyAdapter
        binding.spinnerOutputCurrency.adapter = spinnerAdapter
        viewModel.currencies.observe(this) { currencies ->
            currencyAdapter.submitList(currencies)
            spinnerAdapter.clear()
            spinnerAdapter.addAll(currencies.map { CurrencyCode(it.id, it.charCode) })
        }

        viewModel.isRefreshing.observe(this) {
            binding.swipeRefresh.isRefreshing = it
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshCurrencies()
        }

        lifecycleScope.launchWhenResumed {
            while (true) {
                viewModel.refreshCurrencies()
                delay(10000)
            }
        }

        binding.spinnerOutputCurrency.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    pos: Int,
                    id: Long
                ) {
                    val currencyId = (parent.getItemAtPosition(pos) as? CurrencyCode)?.id
                    if (currencyId != null) {
                        viewModel.selectCurrency(currencyId)
                        binding.etInput.text.toString().toBigDecimalOrNull()?.let {
                            viewModel.convert(it)
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        binding.etInput.doOnTextChanged { text, _, _, _ ->
            text.toString().toBigDecimalOrNull()?.let { viewModel.convert(it) }
        }

        viewModel.convertOutput.observe(this) {
            binding.tvOutput.text = DecimalFormat("#.####").format(it)
        }
    }

}