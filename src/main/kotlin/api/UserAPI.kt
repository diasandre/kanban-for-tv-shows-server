package api

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.User
import models.UserCreateDTO
import org.koin.ktor.ext.inject
import org.litote.kmongo.coroutine.CoroutineDatabase

const val collection = "users"

fun Route.userRoutes() {
    val database: CoroutineDatabase by inject()

    route("/users") {

        get {
            val users = database
                .getCollection<User>(collection)
                .find()
                .toList()

            call.respond(HttpStatusCode.OK, users)
        }

        post {
            val dto = call.receive<UserCreateDTO>()
            val user = User(dto)

            database
                .getCollection<User>(collection)
                .insertOne(user)

            call.respond(HttpStatusCode.OK, user)
        }
    }
}