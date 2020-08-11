package dev.ruanvictor.database

import dev.ruanvictor.web.responses.ExchangeResponse
import dev.ruanvictor.util.Formatter
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object ExchangeTable : Table("exchange") {
    val userId = integer("user_id")
    val createdAt = datetime("created_at")
    val originCurrency = varchar("origin_currency", 3)
    val originValue = decimal("origin_value", 15, 2)
    val targetCurrency = varchar("target_currency", 3)
    val rate = decimal("rate", 15, 10)
}

fun ExchangeTable.rowToExchangeResponse(row: ResultRow): ExchangeResponse =
        ExchangeResponse( row[userId],row[originCurrency], row[originValue],
                row[targetCurrency],  Formatter().getFormattedAmount(row[originValue].multiply(row[rate])),
                row[rate], Formatter().getFormattedDate(row[createdAt])
        )
