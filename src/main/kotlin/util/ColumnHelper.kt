package util

import models.ColumnDTO
import models.User
import org.litote.kmongo.Id

const val WATCHING = "Watching"
const val TO_WATCH = "To watch"
const val AWAITING = "Awaiting"

fun defaultColumns(userId: Id<User>) = listOf(
    ColumnDTO(WATCHING, userId),
    ColumnDTO(TO_WATCH, userId),
    ColumnDTO(AWAITING, userId)
)