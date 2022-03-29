package ru.pavelapk.currency_rates

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.pavelapk.currency_rates.data.Currency
import ru.pavelapk.currency_rates.databinding.ItemCurrencyBinding
import java.text.DecimalFormat

class CurrencyAdapter : ListAdapter<Currency, CurrencyAdapter.ViewHolder>(diff) {
    private companion object {
        val diff = object : DiffUtil.ItemCallback<Currency>() {
            override fun areItemsTheSame(old: Currency, new: Currency) =
                old.id == new.id

            override fun areContentsTheSame(old: Currency, new: Currency) =
                old == new

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding by viewBinding(ItemCurrencyBinding::bind)

        fun bind(currency: Currency) {
            with(binding) {
                tvCharCode.text = currency.charCode
                tvName.text = currency.name
                tvValue.text = currency.value.toString()
                tvDiff.text = DecimalFormat("#.####").apply {
                    positivePrefix = "+"
                }.format(currency.diff)

                if (currency.diff.signum() < 0) { // if negative
                    tvDiff.setTextColor(Color.RED)
                } else {
                    tvDiff.setTextColor(Color.GREEN)
                }
            }
        }
    }
}