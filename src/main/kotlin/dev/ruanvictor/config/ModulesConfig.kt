package dev.ruanvictor.config

import dev.ruanvictor.database.ExchangeRepo
import dev.ruanvictor.services.ExchangeService
import dev.ruanvictor.services.ExchangeServiceImpl
import dev.ruanvictor.services.RatesService
import dev.ruanvictor.services.RatesServiceImpl
import dev.ruanvictor.web.controllers.ExchangeController
import org.koin.dsl.module

object ModulesConfig {
    private val configModule = module {
        single { DBConfig() }
        single { Router(get()) }
    }
    private val exchangeModule = module {
        single { ExchangeRepo() }
        single { RatesServiceImpl() as RatesService }
        single { ExchangeServiceImpl(get(), get()) as ExchangeService }
        single { ExchangeController(get()) }
    }

    internal val allModules = listOf(configModule, exchangeModule)
}
