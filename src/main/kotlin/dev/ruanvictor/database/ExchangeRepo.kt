package dev.ruanvictor.database

import dev.ruanvictor.web.responses.ExchangeResponse
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class ExchangeRepo {

    fun insert(exchangeRecord: ExchangeRecord) : Int =
        transaction {
            ExchangeTable.insert {
                it[userId] = exchangeRecord.user
                it[createdAt] = exchangeRecord.createdAt
                it[originCurrency] = exchangeRecord.originCurrency
                it[originValue] = exchangeRecord.originValue
                it[targetCurrency] = exchangeRecord.targetCurrency
                it[rate] = exchangeRecord.rate
            }.get(ExchangeTable.userId)
        }

    fun findAllByUserId(id: Int): List<ExchangeResponse> =
        transaction {
            ExchangeTable
                .select(Op.build { ExchangeTable.userId.eq(id) })
                .map { resultRow -> ExchangeTable.rowToExchangeResponse(resultRow) }
                .toList()
        }
}
