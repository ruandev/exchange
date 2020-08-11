package dev.ruanvictor.services

import org.json.JSONObject
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class RatesServiceImpl : RatesService {

    override fun retrieveRates(baseCurrency: String, flowId: String?) : MutableMap<String, Any>? {
        val httpClient: HttpClient = HttpClient.newBuilder().build()
        val requestHead = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("https://api.exchangeratesapi.io/latest?base=${baseCurrency}"))
            .build()

        val httpResponse = httpClient.send(requestHead, HttpResponse.BodyHandlers.ofString())

        if(httpResponse.statusCode() != 200 ) {
            PrintLogger().log(Level.ERROR, "FlowId: $flowId Error trying to retrieve rates. " +
                    "Error: ${httpResponse.body()}")
            throw Exception("Error trying to retrieve rates. ${httpResponse.body()}")
        }

        return try {
            JSONObject(httpResponse.body()).getJSONObject("rates").toMap()
        } catch (e: Exception) {
            PrintLogger().log(Level.ERROR, "FlowId: $flowId Error when trying to convert the rates response")
            null
        }
    }
}
