package workers.user

import dao.UserDao
import models.Column
import models.User
import models.UserCreateDTO
import org.litote.kmongo.Id
import util.inject

class UserWorker {
    private val userDao: UserDao by inject()

    suspend fun save(dto: UserCreateDTO) {
        val defaultCreatedColumns = emptyList<Id<Column>>()
        val user = User(dto, defaultCreatedColumns)
        userDao.save(user)
    }

    suspend fun listAll() = userDao.listAll()

    suspend fun get(id: String) = userDao.get(id)
}