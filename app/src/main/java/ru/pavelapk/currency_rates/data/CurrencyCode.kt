package ru.pavelapk.currency_rates.data

data class CurrencyCode(
    val id: String,
    val charCode: String
) {
    override fun toString() = charCode
}
