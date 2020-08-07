package dev.ruanvictor.util

class Validator {
    fun validateCurrencyInRequest(currency: String) : Boolean {
        if (currency.length == 3) {
            return true
        }
        return false
    }
}
