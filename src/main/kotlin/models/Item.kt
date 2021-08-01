package models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class Item(
    @BsonId val id: Id<Item>,
    val userId: Id<User>,
    val tvShow: Id<TvShow>,
    val watchedEpisodes: List<Id<Episode>>,
    val watchedSeasons: List<Id<Season>>
)
