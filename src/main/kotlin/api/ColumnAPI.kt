package api

import br.com.dias.andre.userId
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import util.inject
import workers.column.ColumnWorker
import workers.user.UserWorker

fun Route.columnRoutes() {
    val userWorker: UserWorker by inject()
    val columnWorker: ColumnWorker by inject()

    route("/column") {

        authenticate("firebase") {
            get("/all/user/{id}") {
                val columns = columnWorker.listColumnsByUser(call.userId)
                call.respond(HttpStatusCode.OK, columns)
            }
        }
    }
}
