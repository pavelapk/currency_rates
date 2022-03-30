package ru.pavelapk.currency_rates

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DividerItemDecoration
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.pavelapk.currency_rates.data.CurrencyCode
import ru.pavelapk.currency_rates.databinding.ActivityMainBinding
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel by viewModels<CurrenciesViewModel>()

    private val currencyAdapter = CurrencyAdapter()
    private val spinnerAdapter by lazy {
        ArrayAdapter<CurrencyCode>(this, android.R.layout.simple_spinner_dropdown_item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.recycler.addItemDecoration(dividerItemDecoration)

        binding.recycler.adapter = currencyAdapter
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

        binding.spinnerOutputCurrency.adapter = spinnerAdapter
        binding.spinnerOutputCurrency.onItemSelectedListener = currencySelectListener

        binding.etInput.doOnTextChanged { text, _, _, _ ->
            viewModel.convert(text.toString())
        }

        viewModel.convertOutput.observe(this) {
            binding.tvOutput.text = DecimalFormat("#.####").format(it)
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it ?: getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
        }
    }

    private val currencySelectListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            val currencyId = (parent.getItemAtPosition(pos) as? CurrencyCode)?.id
            if (currencyId != null) {
                viewModel.selectCurrency(currencyId)
                viewModel.convert(binding.etInput.text.toString())
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

}