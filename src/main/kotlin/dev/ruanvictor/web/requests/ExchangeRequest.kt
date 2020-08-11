package dev.ruanvictor.web.requests

import dev.ruanvictor.database.ExchangeRecord
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId

data class ExchangeRequest (
    val user: Int,
    val currencyFrom: String,
    val amount: BigDecimal,
    val currencyTo: String,
    val flowId: String
)

fun ExchangeRequest.toExchangeRecord(rate: BigDecimal) : ExchangeRecord {
    return ExchangeRecord(this.user, this.currencyFrom, this.amount, this.currencyTo, rate, LocalDateTime.now(ZoneId.of("UTC")))
}
