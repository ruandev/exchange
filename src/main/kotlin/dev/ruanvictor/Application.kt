package dev.ruanvictor

import dev.ruanvictor.web.controllers.ExchangeController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import org.koin.core.KoinComponent
import org.koin.core.inject

class Application : KoinComponent {
    private val exchangeController by inject<ExchangeController>()

    fun run() {
        val app = Javalin.create().start(7000)

        app.routes {
            get("exchange", exchangeController::exchange)

            get("exchange/:userId", exchangeController::exchangesByUserId)
        }
    }
}
