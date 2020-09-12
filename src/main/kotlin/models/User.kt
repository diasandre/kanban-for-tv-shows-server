package models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.id.StringId

data class UserCreateDTO(
    val id: String,
    val email: String,
    val name: String
)

data class User(
    @BsonId
    val _id: Id<User>,
    val name: String,
    val email: String,
    val columns: List<Id<Column>>
) {
    constructor(dto: UserCreateDTO, columns: List<Id<Column>>) : this(
        StringId<User>(dto.id),
        dto.name,
        dto.email,
        columns
    )
}
