package models

import io.ktor.auth.Principal

data class FirebasePrincipal(val userId: String) : Principal
