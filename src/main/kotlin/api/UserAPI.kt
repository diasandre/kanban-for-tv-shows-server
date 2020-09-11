package api

import extensions.getId
import extensions.toJson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.UserCreateDTO
import util.inject
import workers.user.UserWorker

fun Route.userRoutes() {
    val userWorker: UserWorker by inject()

    route("/user") {
        get("/{id}") {
            val user = userWorker.get(call.getId())
            call.respond(HttpStatusCode.OK, user.toJson())
        }

        get("/all") {
            val users = userWorker.listAll()
            call.respond(HttpStatusCode.OK, users)
        }

        post {
            val dto = call.receive<UserCreateDTO>()
            userWorker.save(dto)
            call.respond(HttpStatusCode.OK, dto)
        }
    }
}