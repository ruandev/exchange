package dev.ruanvictor.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.assertNotNull

class ExchangeRepoTest : KoinTest {
    private val repo = ExchangeRepo()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()

        val exchangeModule = module {
            single { ExchangeRepo() }
        }

        modules(exchangeModule)
    }

    @Before
    fun setup() {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
        transaction {
            SchemaUtils.create(ExchangeTable)
        }
    }

    @Test
    fun `should insert a new exchange with success`() {
        val id = repo.insert(ExchangeRecord(1, "USD",
            BigDecimal.ZERO, "BRL", BigDecimal.TEN, LocalDateTime.now()))

        assertNotNull(id)
    }

    @Test(expected = IllegalStateException::class)
    fun `should generate an exception if exchange record has an invalid field`() {
        repo.insert(ExchangeRecord(1, "QWERER",
            BigDecimal.ZERO, "BRL", BigDecimal.TEN, LocalDateTime.now()))
    }

}
