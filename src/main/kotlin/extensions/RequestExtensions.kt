package extensions

import io.ktor.application.*

fun ApplicationCall.getId(): String = requireNotNull(this.parameters["id"])