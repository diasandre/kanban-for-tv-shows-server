package api

import extensions.getId
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import workers.kanban.KanbanWorker

fun Route.kanbanRoutes() {
    val kanbanWorker: KanbanWorker by inject()

    route("/kanban") {
        get("/load/user/{id}") {
            val userId = call.getId()
            val data = kanbanWorker.build(userId)
            call.respond(HttpStatusCode.OK, data)
        }
    }
}