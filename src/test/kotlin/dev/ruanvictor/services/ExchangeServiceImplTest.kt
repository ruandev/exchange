package dev.ruanvictor.services

import dev.ruanvictor.config.ModulesConfig.allModules
import dev.ruanvictor.web.requests.ExchangeRequest
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import java.math.BigDecimal

class ExchangeServiceImplTest  : KoinTest {

    private val exchangeService : ExchangeService by inject()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(allModules)
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mock(clazz.java)
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun `should generate an exception if rates for null`() {
        declareMock<RatesService> {
            given(retrieveRates(anyString(), anyString())).will { null }
        }

        val exRequest = ExchangeRequest(1, anyString(), BigDecimal.TEN, "XXX", anyString())

        exchangeService.saveExchange(exRequest)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should generate an exception if currency to if unknown`() {
        val ret = mutableMapOf("ABC" to 1)

        declareMock<RatesService> {
            given(retrieveRates(anyString(), anyString())).will { ret }
        }

        val exRequest = ExchangeRequest(1, anyString(), BigDecimal.TEN, "XXX", anyString())

        exchangeService.saveExchange(exRequest)
    }
}
