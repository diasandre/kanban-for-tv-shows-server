package br.com.dias.andre

import exceptions.AuthenticationException
import exceptions.AuthorizationException
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.tomcat.EngineMain.main
import io.ktor.util.*

fun main(args: Array<String>): Unit = main(args)

fun Application.module(testing: Boolean = false) {

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
            validate { if (it.name == login() && it.password == password()) UserIdPrincipal(it.name) else null }
        }
    }

    install(ContentNegotiation) {
        gson {
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
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        authenticate("Auth") {
            get("/protected/route/basic") {
                val principal = call.principal<UserIdPrincipal>()
                call.respondText("Hello ${principal?.name}")
            }
        }
    }
}

@KtorExperimentalAPI
fun Application.login() = environment.config.property("ktor.login").getString()

@KtorExperimentalAPI
fun Application.password() = environment.config.property("ktor.password").getString()
