package models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class Episode(@BsonId val id: Id<Episode>, val title: String, val season: Id<Season>, val tvShow: Id<TvShow>)
