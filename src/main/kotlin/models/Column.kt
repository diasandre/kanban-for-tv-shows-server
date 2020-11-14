package models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.id.ObjectIdToStringGenerator.newStringId
import org.litote.kmongo.id.StringId

data class ColumnDTO(
    val id: String? = null,
    val title: String,
    val userId: Id<User>,
    val items: List<Id<Item>> = emptyList()
) {
    constructor(title: String, userId: Id<User>) : this(
        null,
        title,
        userId
    )

    constructor(column: Column) : this(
        column._id.id,
        column.title,
        column.userId,
        column.items
    )
}

data class Column(
    @BsonId val _id: StringId<Column>,
    val userId: Id<User>,
    val title: String,
    val items: List<Id<Item>>
) {
    constructor(columnDTO: ColumnDTO) : this(
        columnDTO.id?.let { StringId(columnDTO.id) } ?: newStringId(),
        columnDTO.userId,
        columnDTO.title,
        columnDTO.items
    )
}