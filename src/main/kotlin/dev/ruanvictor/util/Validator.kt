package dev.ruanvictor.util

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
}
