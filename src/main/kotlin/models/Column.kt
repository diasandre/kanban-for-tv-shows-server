package models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.id.ObjectIdToStringGenerator.newStringId
import org.litote.kmongo.id.StringId

data class ColumnDTO(val title: String, val userId: Id<User>, val items: List<Id<Item>> = emptyList())

data class Column(@BsonId val _id: StringId<Column>, val userId: Id<User>, val title: String, val items: List<Id<Item>>){
    constructor(columnDTO: ColumnDTO) : this(
        newStringId(),
        columnDTO.userId,
        columnDTO.title,
        columnDTO.items
    )
}