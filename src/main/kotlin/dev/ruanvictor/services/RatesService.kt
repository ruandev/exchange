package dev.ruanvictor.services

interface RatesService {
    fun retrieveRates(baseCurrency: String, flowId: String?) : MutableMap<String, Any>?
}
