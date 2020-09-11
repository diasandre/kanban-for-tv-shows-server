package dao

import models.Column
import models.User
import org.bson.BsonValue
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import util.inject

class ColumnDao {
    private val database: CoroutineDatabase by inject()
    private val collection = "columns"

    suspend fun listAllByUser(userId: Id<User>) = database
        .getCollection<Column>(collection)
        .find(Column::userId eq userId)
        .toList()

    suspend fun save(column: Column) = database
        .getCollection<Column>(collection)
        .insertOne(column)

    suspend fun saveAll(columns: List<Column>): MutableMap<Int, BsonValue> = database
        .getCollection<Column>(collection)
        .insertMany(columns)
        .insertedIds
}