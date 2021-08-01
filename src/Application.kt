package br.com.dias.andre

import api.columnRoutes
import api.kanbanRoutes
import api.userRoutes
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import configuration.firebase
import exceptions.AuthenticationException
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.auth.principal
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.tomcat.EngineMain.main
import models.FirebasePrincipal
import org.koin.core.context.startKoin
import org.litote.kmongo.Id
import org.litote.kmongo.toId
import java.io.FileInputStream

fun main(args: Array<String>): Unit = main(args)

val ApplicationCall.userId: String
    get() = principal<FirebasePrincipal>()?.userId ?: throw AuthenticationException()

fun Application.module(testing: Boolean = false) {

    val serviceAccount = FileInputStream("tv-show-kanban-firebase.json")

    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("https://tv-show-kanban.firebaseio.com")
        .build()

    FirebaseApp.initializeApp(options)

    startKoin {
        modules(applicationModule)
    }

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header(HttpHeaders.AccessControlAllowHeaders) // temporary
        header(HttpHeaders.ContentType) // temporary
        header(HttpHeaders.AccessControlAllowOrigin) // temporary
        allowCredentials = true
        anyHost() // temporary
    }

    authentication {
        firebase("firebase", FirebaseApp.getInstance()) {
            validate { credential ->
                FirebasePrincipal(
                    userId = credential.token.uid
                )
            }
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
            log.info(cause.localizedMessage)
            call.respond(HttpStatusCode.Unauthorized)
        }
    }

    routing {

        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        authenticate("firebase") {
            get("/protected/route/basic") {
                call.respondText("Hello ${call.userId}")
            }
        }

        userRoutes()
        columnRoutes()
        kanbanRoutes()
    }
}
