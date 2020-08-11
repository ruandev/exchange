package dev.ruanvictor.services

import dev.ruanvictor.database.ExchangeRepo
import dev.ruanvictor.database.toExchangeResponse
import dev.ruanvictor.web.requests.ExchangeRequest
import dev.ruanvictor.web.requests.toExchangeRecord
import dev.ruanvictor.web.responses.ExchangeResponse
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger
import java.math.BigDecimal

class ExchangeServiceImpl (private val exchangeRepo: ExchangeRepo, private val ratesService: RatesService) : ExchangeService {

    override fun saveExchange(exchangeRequest: ExchangeRequest): ExchangeResponse {
        val rates = ratesService.retrieveRates(exchangeRequest.currencyFrom, exchangeRequest.flowId)
        validateRates(rates, exchangeRequest)

        val rate = BigDecimal(rates?.get(exchangeRequest.currencyTo).toString())

        val exchangeRecord = exchangeRequest.toExchangeRecord(rate)
        exchangeRepo.insert(exchangeRecord)

        val result = exchangeRequest.amount.multiply(rate)
        return exchangeRecord.toExchangeResponse(result)
    }

    override fun listExchangesByUserId(userId: Int, flowId: String): List<ExchangeResponse> {
        return exchangeRepo.findAllByUserId(userId)
    }

    private fun validateRates(rates: MutableMap<String, Any>?, exchangeRequest: ExchangeRequest) {
        if(rates.isNullOrEmpty()) {
            PrintLogger().log(
                Level.ERROR, "FlowId: ${exchangeRequest.flowId}" +
                        "No rate found for currency ${exchangeRequest.currencyFrom}")
            throw UninitializedPropertyAccessException("No rate found for currency ${exchangeRequest.currencyFrom}")
        } else {
            if(!rates.keys.contains(exchangeRequest.currencyTo)) {
                throw IllegalArgumentException("Currency ${exchangeRequest.currencyTo} is not supported")
            }
        }
    }
}
