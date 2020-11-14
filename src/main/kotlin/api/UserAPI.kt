package api

import exceptions.UserNotFoundException
import extensions.getId
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