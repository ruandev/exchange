package dev.ruanvictor.services

import dev.ruanvictor.database.ExchangeRepo
import dev.ruanvictor.database.toExchangeResponse
import dev.ruanvictor.web.requests.ExchangeRequest
import dev.ruanvictor.web.requests.toExchangeRecord
import dev.ruanvictor.web.responses.ExchangeResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal

class ExchangeServiceImpl (private val exchangeRepo: ExchangeRepo, private val ratesService: RatesService) : ExchangeService {
    private val LOG: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    override fun saveExchange(exchangeRequest: ExchangeRequest): ExchangeResponse {
        val rates = ratesService.retrieveRates(exchangeRequest.currencyFrom, exchangeRequest.flowId)
        validateRates(rates, exchangeRequest)

        val rate = BigDecimal(rates?.get(exchangeRequest.currencyTo).toString())

        val exchangeRecord = exchangeRequest.toExchangeRecord(rate)
        exchangeRepo.insert(exchangeRecord)

        LOG.info("FlowId: ${exchangeRequest.flowId} Msg: Exchange save in database. ")

        val result = exchangeRequest.amount.multiply(rate)
        return exchangeRecord.toExchangeResponse(result)
    }

    override fun listExchangesByUserId(userId: Int, flowId: String): List<ExchangeResponse> {
        return exchangeRepo.findAllByUserId(userId)
    }

    private fun validateRates(rates: MutableMap<String, Any>?, exchangeRequest: ExchangeRequest) {
        if(rates.isNullOrEmpty()) {
            LOG.error("FlowId: ${exchangeRequest.flowId} Msg: No rate found for currency ${exchangeRequest.currencyFrom}")
            throw UninitializedPropertyAccessException("No rate found for currency ${exchangeRequest.currencyFrom}")
        } else {
            if(!rates.keys.contains(exchangeRequest.currencyTo)) {
                LOG.error("FlowId: ${exchangeRequest.flowId} Msg: Currency ${exchangeRequest.currencyTo} is not supported")
                throw IllegalArgumentException("Currency ${exchangeRequest.currencyTo} is not supported")
            }
        }
    }
}
