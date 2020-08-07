package dev.ruanvictor.requests

import dev.ruanvictor.database.ExchangeRecord
import java.math.BigDecimal
import java.time.LocalDateTime

data class ExchangeRequest (
    val user: Int,
    val currencyFrom: String,
    val amount: BigDecimal,
    val currencyTo: String
)

fun ExchangeRequest.toExchangeRecord(rate: BigDecimal) : ExchangeRecord {
    return ExchangeRecord(this.user, this.currencyFrom, this.amount, this.currencyTo, rate, LocalDateTime.now())
}
