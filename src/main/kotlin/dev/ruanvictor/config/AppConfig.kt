package dev.ruanvictor.config

import dev.ruanvictor.config.ModulesConfig.allModules
import io.javalin.Javalin
import io.javalin.plugin.openapi.OpenApiOptions
import io.javalin.plugin.openapi.OpenApiPlugin
import io.javalin.plugin.openapi.ui.SwaggerOptions
import io.swagger.v3.oas.models.info.Info
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

        return Javalin.create{ config ->
            config.registerPlugin(getConfiguredOpenApiPlugin())
        }.also { app ->
                    router.register(app)
                }
    }

    private fun getConfiguredOpenApiPlugin() = OpenApiPlugin(
        OpenApiOptions(
            Info().apply {
                version("1.0")
                description("Exchange API")
            }
        ).apply {
            path("/swagger-docs")
            swagger(SwaggerOptions("/swagger-ui").title("Exchange API Docs"))

        }
    )
}
