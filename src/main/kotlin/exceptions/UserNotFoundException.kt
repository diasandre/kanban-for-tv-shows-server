package exceptions

import io.ktor.server.plugins.BadRequestException

class UserNotFoundException : BadRequestException("User not found")
