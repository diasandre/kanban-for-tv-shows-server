package models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class Season(@BsonId val id: Id<Season>, val number: Int, val episodes: List<Id<Episode>>)
