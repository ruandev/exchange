package dev.ruanvictor.util

import java.math.BigDecimal
import java.math.RoundingMode.CEILING
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_DATE_TIME

class Formatter {

    fun getFormattedDate( dateTime: LocalDateTime ) : String {
        return dateTime.format(ISO_DATE_TIME)
    }

    fun getFormattedAmount( amount: BigDecimal ) : BigDecimal {
        return amount.setScale(2, CEILING)
    }
}
