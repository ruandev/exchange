package dev.ruanvictor.services

import dev.ruanvictor.web.requests.ExchangeRequest
import dev.ruanvictor.web.responses.ExchangeResponse

interface ExchangeService {
    fun saveExchange(exchangeRequest: ExchangeRequest): ExchangeResponse

    fun listExchangesByUserId(userId: Int, flowId: String): List<ExchangeResponse>
}
