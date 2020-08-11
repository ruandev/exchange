package dev.ruanvictor.config

import dev.ruanvictor.database.ExchangeTable
import org.h2.tools.Server
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DBConfig {
    init {
        Server.createWebServer().start()
        Database.connect("jdbc:h2:mem:exchange;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

        transaction { SchemaUtils.create(ExchangeTable) }
    }
}
