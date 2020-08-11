package dev.ruanvictor.web.controllers

import dev.ruanvictor.services.ExchangeService
import dev.ruanvictor.util.Validator
import dev.ruanvictor.web.requests.ExchangeRequest
import io.javalin.http.Context
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger
import java.math.BigDecimal
import java.util.UUID.randomUUID

class ExchangeController (private val exchangeService: ExchangeService)  {

    fun exchange(ctx: Context) {
        val flowId = randomUUID().toString()

        val currencyFrom = ctx.queryParam<String>("currencyFrom")
                .check({ Validator().isValidCurrency(it) }, "The currency must have only 3 letters")
                .get()

        val amount = ctx.queryParam<Double>("amount")
                .check({ Validator().isValidAmount(it) }, "The amount must have a positive value")
                .get()

        val currencyTo = ctx.queryParam<String>("currencyTo")
                .check({ Validator().isValidCurrency(it) }, "The currency must have only 3 letters")
                .get()

        val user = ctx.header<Int>("user_id").get()

        PrintLogger().log(Level.INFO, "Flow Id: $flowId " +
                "CurrencyFrom: $currencyFrom " +
                "Amount: $amount " +
                "CurrencyTo: $currencyTo")

        try {
            val exchangeRecord = exchangeService.saveExchange(ExchangeRequest(user, currencyFrom, BigDecimal(amount), currencyTo, flowId))
            ctx.header("flowId", flowId).status(200).json(exchangeRecord)
        } catch (e: Exception) {
            ctx.header("flowId", flowId).status(400).json(e.localizedMessage)
        }
    }

    fun exchangesByUserId(ctx: Context) {
        val flowId = randomUUID().toString()

        val userId = ctx.pathParam("userId", Int::class.java).get()

        PrintLogger().log(Level.INFO, "Flow Id: $flowId " +
                "UserID: $userId ")

        try {
            val exchangeRecord = exchangeService.listExchangesByUserId(userId, flowId)
            val statusCode = if(exchangeRecord.isNullOrEmpty()) 204 else 200
            ctx.header("flowId", flowId).status(statusCode).json(exchangeRecord)
        } catch (e: Exception) {
            ctx.header("flowId", flowId).status(400).json(e.localizedMessage)
        }
    }
}
