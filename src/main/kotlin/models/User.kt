package models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class UserCreateDTO(
    val id: String,
    val email: String,
    val name: String
)

data class User(
    @BsonId
    val id: Id<User>,
    val googleId: String,
    val name: String,
    val email: String,
    val columns: List<Id<Column>>
) {
    constructor(dto: UserCreateDTO, columns: List<Id<Column>>) : this(
        newId(),
        dto.id,
        dto.name,
        dto.email,
        columns
    )
}
