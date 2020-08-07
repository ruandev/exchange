package dev.ruanvictor.util

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidatorTest {

    @Test
    fun `should return false if the currency is longer than 3 characters`() {
        val isValid = Validator().validateCurrencyInRequest("QWER")
        assertFalse(isValid)
    }

    @Test
    fun `should return false if the currency is less than 3 characters`() {
        val isValid = Validator().validateCurrencyInRequest("QW")
        assertFalse(isValid)
    }

    @Test
    fun `should return true if the currency is 3 characters long`() {
        val isValid = Validator().validateCurrencyInRequest("QWE")
        assertTrue(isValid)
    }
}
