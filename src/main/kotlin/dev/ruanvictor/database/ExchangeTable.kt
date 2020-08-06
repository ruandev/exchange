package dev.ruanvictor.database

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.math.BigDecimal
import java.time.LocalDateTime

object ExchangeTable : Table("exchange") {
    val userId = integer("user_id")
    val createdAt = datetime("created_at")
    val originCurrency = varchar("origin_currency", 3)
    val originValue = decimal("origin_value", 15, 2)
    val targetCurrency = varchar("target_currency", 3)
    val rate = decimal("rate", 15, 2)
}

data class ExchangeRecord(
    val user: Int,
    val originCurrency: String,
    val originValue: BigDecimal,
    val targetCurrency: String,
    val rate: BigDecimal,
    val createdAt: LocalDateTime
)
