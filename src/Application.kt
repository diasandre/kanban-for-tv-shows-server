package br.com.dias.andre

import api.userRoutes
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import com.google.gson.annotations.JsonAdapter
import exceptions.AuthenticationException
import exceptions.AuthorizationException
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.tomcat.EngineMain.main
import io.ktor.sessions.*
import io.ktor.util.*
import org.koin.core.context.startKoin
import org.koin.ktor.ext.Koin
import org.litote.kmongo.Id
import org.litote.kmongo.toId

fun main(args: Array<String>): Unit = main(args)

@KtorExperimentalAPI
fun Application.module(testing: Boolean = false) {

    val login = environment.config.property("ktor.login").getString()
    val password = environment.config.property("ktor.password").getString()

    startKoin {
        modules(applicationModule)
    }

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        allowCredentials = true
    }

    install(Authentication) {
        basic("Auth") {
            realm = "Server"
            validate { if (it.name == login && it.password == password) UserIdPrincipal(it.name) else null }
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
        exception<AuthenticationException> { cause ->
            call.respond(HttpStatusCode.Unauthorized)
        }
        exception<AuthorizationException> { cause ->
            call.respond(HttpStatusCode.Forbidden)
        }
    }

    routing {
        intercept(ApplicationCallPipeline.Features) {
            //temporary
            val token = call.request.headers["token"]
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
    }
}

