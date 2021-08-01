package models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class TvShow(@BsonId val id: Id<TvShow>, val imdbID: String, val title: String, val seasons: List<Id<Season>>)
