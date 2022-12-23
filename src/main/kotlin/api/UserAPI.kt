package api

import exceptions.UserNotFoundException
import extensions.getId
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import models.UserCreateDTO
import org.koin.ktor.ext.inject
import workers.user.UserWorker

fun Route.userRoutes() {
    val userWorker: UserWorker by inject()

    route("/user") {
        get("/{id}") {
            val user = userWorker.get(call.getId()) ?: throw UserNotFoundException()
            call.respond(HttpStatusCode.OK, user)
        }

        get("/all") {
            val users = userWorker.listAll()
            call.respond(HttpStatusCode.OK, users)
        }

        post("/getOrCreate") {
            val dto = call.receive<UserCreateDTO>()
            val user = userWorker.get(dto.id) ?: userWorker.save(dto)
            call.respond(HttpStatusCode.OK, user)
        }

        post {
            val dto = call.receive<UserCreateDTO>()
            val user = userWorker.save(dto)
            call.respond(HttpStatusCode.OK, user)
        }
    }
}
