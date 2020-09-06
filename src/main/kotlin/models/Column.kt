package models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class Column(@BsonId val id: Id<Column>, val items: List<Id<Item>>)