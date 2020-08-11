package dev.ruanvictor.database

import dev.ruanvictor.web.responses.ExchangeResponse
import dev.ruanvictor.util.Formatter
import java.math.BigDecimal
import java.time.LocalDateTime

data class ExchangeRecord(
    val user: Int,
    val originCurrency: String,
    val originValue: BigDecimal,
    val targetCurrency: String,
    val rate: BigDecimal,
    val createdAt: LocalDateTime
)

fun ExchangeRecord.toExchangeResponse(resultAmount: BigDecimal) : ExchangeResponse =
    ExchangeResponse(this.user, this.originCurrency, this.originValue, this.targetCurrency,
            Formatter().getFormattedAmount(resultAmount), this.rate, Formatter().getFormattedDate(this.createdAt))
