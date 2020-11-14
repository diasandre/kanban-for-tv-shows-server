package dao

import models.Item
import models.User
import org.litote.kmongo.Id
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import util.inject

class ItemDao {
    private val database: CoroutineDatabase by inject()
    private val collection = "items"

    suspend fun listAll() = database
        .getCollection<Item>(collection)
        .find()
        .toList()

    suspend fun listAllByUser(userId: Id<User>) = database
        .getCollection<Item>(collection)
        .find(Item::userId eq userId)
        .toList()

    suspend fun save(item: Item) = database
        .getCollection<Item>(collection)
        .insertOne(item)

    suspend fun get(id: String) = database
        .getCollection<User>(collection)
        .findOneById(id)
}