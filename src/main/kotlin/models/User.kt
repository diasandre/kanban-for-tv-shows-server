package models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class UserCreateDTO(
    val name: String
)

data class User(
    @BsonId
    val id: Id<User> = newId(),
    val name: String,
    val columns: List<Long> = emptyList()
) {
    constructor(dto: UserCreateDTO) : this(
        name = dto.name
    )
}
