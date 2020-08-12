package dev.ruanvictor.services

import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class RatesServiceImpl : RatesService {
    private val LOG: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    override fun retrieveRates(baseCurrency: String, flowId: String?) : MutableMap<String, Any>? {
        val httpClient: HttpClient = HttpClient.newBuilder().build()
        val requestHead = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("https://api.exchangeratesapi.io/latest?base=${baseCurrency}"))
            .build()

        val httpResponse = httpClient.send(requestHead, HttpResponse.BodyHandlers.ofString())

        if(httpResponse.statusCode() != 200 ) {
            val err = JSONObject(httpResponse.body()).get("error")
            LOG.error("FlowId: $flowId Msg: Error trying to retrieve rates. $err")
            throw Exception("Error trying to retrieve rates. $err")
        }

        return try {
            LOG.info("FlowId: $flowId Msg: Success in retrieve rates")
            JSONObject(httpResponse.body()).getJSONObject("rates").toMap()
        } catch (e: Exception) {
            LOG.error("FlowId: $flowId Msg: Error when trying to convert the rates response")
            null
        }
    }
}
