package api

import br.com.dias.andre.userId
import exceptions.UserNotFoundException
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import models.UserCreateDTO
import util.inject
import workers.user.UserWorker

fun Route.userRoutes() {
    val userWorker: UserWorker by inject()

    route("/user") {
        authenticate("firebase") {
            get {
                val user = userWorker.get(call.userId) ?: throw UserNotFoundException()
                call.respond(HttpStatusCode.OK, user)
            }

            get("/all") {
                val users = userWorker.listAll()
                call.respond(HttpStatusCode.OK, users)
            }

            post("/getOrCreate") {
                val dto = call.receive<UserCreateDTO>()
                val user = userWorker.get(call.userId) ?: userWorker.save(dto)
                call.respond(HttpStatusCode.OK, user)
            }

            post {
                val dto = call.receive<UserCreateDTO>()
                val user = userWorker.save(dto)
                call.respond(HttpStatusCode.OK, user)
            }
        }
    }
}
