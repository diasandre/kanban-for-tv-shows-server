package exceptions

import io.ktor.features.*

class UserNotFoundException : BadRequestException("User not found")