package dev.ruanvictor.util

import java.math.BigDecimal

class Validator {
    fun isValidCurrency(currency: String) : Boolean {
        val currencyWithoutQuotes = currency.replace("\"", "")
        val regexCurrency = Regex("[^a-z]", RegexOption.IGNORE_CASE)

        if (regexCurrency.containsMatchIn(currencyWithoutQuotes)) {
            return false
        }

        if (currencyWithoutQuotes.length != 3) {
            return false
        }
        return true
    }

    fun isValidAmount(amount: BigDecimal) : Boolean {
        return amount>=BigDecimal.ZERO
    }
}
