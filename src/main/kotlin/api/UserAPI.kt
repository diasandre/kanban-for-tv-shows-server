package api

import extensions.toJson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.UserCreateDTO
import util.inject
import workers.user.UserWorker

const val collection = "users"

fun Route.userRoutes() {

    val userWorker: UserWorker by inject()

    route("/user") {

        get("/{id}") {
            val id = call.parameters["id"]
            requireNotNull(id)
            val user = userWorker.get(id)
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