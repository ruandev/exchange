package dev.ruanvictor.config

import dev.ruanvictor.web.controllers.ExchangeController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import org.koin.core.KoinComponent

class Router (private val exchangeController: ExchangeController) : KoinComponent {
    fun register(app: Javalin) {
        app.routes {
            get("exchange", exchangeController::exchange)

            get("exchange/:userId", exchangeController::exchangesByUserId)
        }
    }
}
