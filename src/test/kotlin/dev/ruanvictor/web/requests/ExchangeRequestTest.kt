package dev.ruanvictor.web.requests

import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ExchangeRequestTest {

    @Test
    fun `should transform an exchange request into an exchange record`() {
        val exchangeRequest = ExchangeRequest(1, "ABC", BigDecimal.TEN, "QWE", "FLWID")
        val exchangeRecord = exchangeRequest.toExchangeRecord(BigDecimal.ZERO)

        assertEquals(exchangeRecord.user, exchangeRequest.user)
        assertEquals(exchangeRecord.originCurrency, exchangeRequest.currencyFrom)
        assertEquals(exchangeRecord.targetCurrency, exchangeRequest.currencyTo)
        assertEquals(exchangeRecord.originValue, exchangeRequest.amount)
        assertEquals(exchangeRecord.rate, BigDecimal.ZERO)
        assertNotNull(exchangeRecord.createdAt)
    }

}
