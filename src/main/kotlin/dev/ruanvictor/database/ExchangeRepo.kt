package dev.ruanvictor.database

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ext.scope
import java.time.LocalDateTime

class ExchangeRepo {

    fun insert(exchangeRecord: ExchangeRecord) : Int =
        transaction {
            ExchangeTable.insertAndGetId {
                it[userId] = exchangeRecord.user
                it[createdAt] = exchangeRecord.createdAt
                it[originCurrency] = exchangeRecord.originCurrency
                it[originValue] = exchangeRecord.originValue
                it[targetCurrency] = exchangeRecord.targetCurrency
                it[rate] = exchangeRecord.rate
            }.value
        }
}
