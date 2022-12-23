package br.com.dias.andre

import api.columnRoutes
import api.kanbanRoutes
import api.userRoutes
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import exceptions.AuthenticationException
import exceptions.AuthorizationException
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.gson.gson
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.basic
import io.ktor.server.auth.principal
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.tomcat.Tomcat
import org.koin.core.context.startKoin
import org.litote.kmongo.Id
import org.litote.kmongo.toId

fun main() {
    embeddedServer(Tomcat, port = 8080) {
        val login = environment.config.property("ktor.login").getString()
        val password = environment.config.property("ktor.password").getString()

        startKoin {
            modules(applicationModule)
        }

        install(CORS) {
            allowMethod(HttpMethod.Options)
            allowMethod(HttpMethod.Put)
            allowMethod(HttpMethod.Delete)
            allowMethod(HttpMethod.Patch)
            allowHeader(HttpHeaders.Authorization)
            allowHeader(HttpHeaders.AccessControlAllowHeaders) // temporary
            allowHeader(HttpHeaders.ContentType) // temporary
            allowHeader(HttpHeaders.AccessControlAllowOrigin) // temporary
            allowCredentials = true
            anyHost() // temporary
        }

        install(Authentication) {
            basic("Auth") {
                realm = "Server"
                validate { if (it.name == br.com.dias.andre.login && it.password == br.com.dias.andre.password) UserIdPrincipal(it.name) else null }
            }
        }

        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
                registerTypeAdapter(Id::class.java, JsonSerializer<Id<Any>> { id, _, _ -> JsonPrimitive(id?.toString()) })
                registerTypeAdapter(Id::class.java, JsonDeserializer<Id<Any>> { id, _, _ -> id.asString.toId() })
            }
        }

        install(StatusPages) {
            exception<AuthenticationException> { call, _ ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { call, _ ->
                call.respond(HttpStatusCode.Forbidden)
            }
        }

        routing {
            intercept(ApplicationCallPipeline.Plugins) {
                // temporary
                val token = call.request.headers["Authorization"]
                if (token != "12345") {
                    call.respondText {
                        "invalid token"
                    }
                    return@intercept finish()
                }
            }

            get("/") {
                call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            }

            authenticate("Auth") {
                get("/protected/route/basic") {
                    val principal = call.principal<UserIdPrincipal>()
                    call.respondText("Hello ${principal?.name}")
                }
            }

            userRoutes()
            columnRoutes()
            kanbanRoutes()
        }
    }.start(wait = true)
}