package dev.ruanvictor.web.responses

import java.math.BigDecimal

data class ExchangeResponse (
    val userId: Int,
    val currencyFrom: String,
    val amountFrom: BigDecimal,
    val currencyTo: String,
    val amountTo: BigDecimal,
    val rate: BigDecimal,
    val date: String
)


