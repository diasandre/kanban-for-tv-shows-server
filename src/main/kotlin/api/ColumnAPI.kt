package api

import extensions.getId
import io.ktor.http.*
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
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