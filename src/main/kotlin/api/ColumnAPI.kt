package api

import extensions.getId
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import util.inject
import workers.column.ColumnWorker
import workers.user.UserWorker

fun Route.columnRoutes() {
    val userWorker: UserWorker by inject()
    val columnWorker: ColumnWorker by inject()

    route("/column") {

        get("/all/user/{id}") {
            val userId = call.getId()
            val columns = columnWorker.listColumnsByUser(userId)
            call.respond(HttpStatusCode.OK, columns)
        }
    }
}