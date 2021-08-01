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
import workers.kanban.KanbanWorker

fun Route.kanbanRoutes() {
    val kanbanWorker: KanbanWorker by inject()

    route("/kanban") {
        authenticate("firebase") {
            get("/load/user/{id}") {
                val data = kanbanWorker.build(call.userId)
                call.respond(HttpStatusCode.OK, data)
            }
        }
    }
}
