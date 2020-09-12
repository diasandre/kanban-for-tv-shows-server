package api

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.UserCreateDTO
import util.inject
import workers.user.UserWorker

fun Route.columnRoutes() {
    val userWorker: UserWorker by inject()

    route("/column") {

        get("/all/{id}") {
            val users = userWorker.listAll()
            call.respond(HttpStatusCode.OK, users)
        }
    }
}