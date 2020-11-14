package api

import extensions.getId
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import util.inject
import workers.column.ColumnWorker
import workers.kanban.KanbanWorker
import workers.user.UserWorker

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