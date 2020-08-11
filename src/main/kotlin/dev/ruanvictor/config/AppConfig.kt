package dev.ruanvictor.config

import dev.ruanvictor.config.ModulesConfig.allModules
import io.javalin.Javalin
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject

class AppConfig : KoinComponent {

    private val router: Router by inject()

    fun setup(): Javalin {
        DBConfig()

        startKoin {
            printLogger()

            modules(allModules)
        }

        return Javalin.create()
                .also { app ->
                    router.register(app)
                }
    }
}
