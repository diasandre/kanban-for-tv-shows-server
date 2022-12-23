package extensions

import io.ktor.server.application.ApplicationCall

fun ApplicationCall.getId(): String = requireNotNull(this.parameters["id"])