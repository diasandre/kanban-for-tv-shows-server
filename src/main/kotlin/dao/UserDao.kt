package dao

import models.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import util.inject

class UserDao {
    private val database: CoroutineDatabase by inject()
    private val collection = "users"

    suspend fun listAll() = database
        .getCollection<User>(collection)
        .find()
        .toList()

    suspend fun save(user: User) = database
        .getCollection<User>(collection)
        .insertOne(user)

    suspend fun get(id: String) = database
        .getCollection<User>(collection)
        .findOne(id)
}