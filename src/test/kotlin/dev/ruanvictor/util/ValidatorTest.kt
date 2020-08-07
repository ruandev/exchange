package dev.ruanvictor.util

import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidatorTest {

    @Test
    fun `should return false if the currency is longer than 3 characters`() {
        val isValid = Validator().isValidCurrency("QWER")
        assertFalse(isValid)
    }

    @Test
    fun `should return false if the currency is less than 3 characters`() {
        val isValid = Validator().isValidCurrency("QW")
        assertFalse(isValid)
    }

    @Test
    fun `should return true if the currency is 3 characters long`() {
        val isValid = Validator().isValidCurrency("QWE")
        assertTrue(isValid)
    }

    @Test
    fun `should return false if the currency has any characters other than letters`() {
        val isValid = Validator().isValidCurrency("1WE")
        assertFalse(isValid)
    }

    @Test
    fun `should remove the quotes and validate only the string without quotes`() {
        val isValid = Validator().isValidCurrency("\"QWE\"")
        assertTrue(isValid)
    }

    @Test
    fun `should return true if currency validates in a case insensitive way`() {
        val isValid = Validator().isValidCurrency("aBc")
        assertTrue(isValid)
    }

    @Test
    fun `should return false must return false if the string contains letters with accents`() {
        val isValid = Validator().isValidCurrency("ãBc")
        assertFalse(isValid)
    }

    @Test
    fun `should return true if the amount is less than zero`() {
        val isValid = Validator().isValidAmount(BigDecimal(-100))
        assertFalse(isValid)
    }

    @Test
    fun `should return false if the amount is greater than zero`() {
        val isValid = Validator().isValidAmount(BigDecimal(100))
        assertTrue(isValid)
    }
}
