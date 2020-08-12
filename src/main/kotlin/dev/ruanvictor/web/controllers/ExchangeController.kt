package dev.ruanvictor.web.controllers

import dev.ruanvictor.services.ExchangeService
import dev.ruanvictor.util.Validator
import dev.ruanvictor.web.requests.ExchangeRequest
import dev.ruanvictor.web.responses.ExchangeResponse
import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.OpenApi
import io.javalin.plugin.openapi.annotations.OpenApiContent
import io.javalin.plugin.openapi.annotations.OpenApiParam
import io.javalin.plugin.openapi.annotations.OpenApiResponse
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.util.UUID.randomUUID

class ExchangeController (private val exchangeService: ExchangeService)  {
    private val LOG = LoggerFactory.getLogger(javaClass.simpleName)

    @OpenApi(
        summary = "Make exchange and save the register",
        operationId = "exchange",
        queryParams = [
            OpenApiParam("currencyFrom", String::class, "The base currency for the exchange", required = true),
            OpenApiParam("currencyTo", String::class, "The target currency for the exchange", required = true),
            OpenApiParam("amount", Double::class, "The amount to be exchanged", required = true)
        ],
        headers = [OpenApiParam("user_id", Int::class, "The user ID", required = true)],
        responses = [
            OpenApiResponse("200", [OpenApiContent(ExchangeResponse::class)]),
            OpenApiResponse("400", description = "Validation errors and other errors known to the api")
        ]
    )
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

        LOG.info("Flow Id: $flowId " +
                "CurrencyFrom: $currencyFrom " +
                "Amount: $amount " +
                "CurrencyTo: $currencyTo " +
                "User: $user")

        try {
            val exchangeRecord = exchangeService.saveExchange(ExchangeRequest(user, currencyFrom, BigDecimal(amount), currencyTo, flowId))
            LOG.info("Flow Id: $flowId Msg: Successful exchange.")
            ctx.header("flowId", flowId).status(200).json(exchangeRecord)
        } catch (e: Exception) {
            LOG.error("Flow Id: $flowId Msg: Error trying to exchange. ${e.localizedMessage}")
            ctx.header("flowId", flowId).status(400).json(e.localizedMessage)
        }
    }

    @OpenApi(
        summary = "List all exchanges by user ID",
        operationId = "exchangesByUserId",
        pathParams = [OpenApiParam("userId", Int::class, "The user ID", required = true)],
        responses = [
            OpenApiResponse("200", [OpenApiContent(ExchangeResponse::class, isArray = true)]),
            OpenApiResponse("204"),
            OpenApiResponse("400", description = "Validation errors and other errors known to the api")
        ]
    )
    fun exchangesByUserId(ctx: Context) {
        val flowId = randomUUID().toString()

        val userId = ctx.pathParam("userId", Int::class.java).get()

        LOG.info("Flow Id: $flowId UserID: $userId ")

        try {
            val exchangeRecord = exchangeService.listExchangesByUserId(userId, flowId)
            val statusCode = if(exchangeRecord.isNullOrEmpty()) 204 else 200
            LOG.info("FlowId: $flowId " +
                    "Msg: Success in retrieving all exchanges for userId " +
                    "StatusCode: $statusCode")
            ctx.header("flowId", flowId).status(statusCode).json(exchangeRecord)
        } catch (e: Exception) {
            LOG.error("FlowId: $flowId Msg: Error when trying to retrieve all exchanges by userId")
            ctx.header("flowId", flowId).status(400).json(e.localizedMessage)
        }
    }
}
