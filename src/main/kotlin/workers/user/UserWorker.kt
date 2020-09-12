package workers.user

import dao.UserDao
import exceptions.UserNotFoundException
import models.Column
import models.User
import models.UserCreateDTO
import org.litote.kmongo.Id
import org.litote.kmongo.id.StringId
import util.inject
import workers.column.ColumnWorker

class UserWorker {
    private val userDao: UserDao by inject()
    private val columnWorker: ColumnWorker by inject()

    suspend fun save(dto: UserCreateDTO): User {
        val defaultCreatedColumns = columnWorker.createDefaultColumns(dto.id)
        return User(dto, defaultCreatedColumns).also { userDao.save(it) }
    }

    suspend fun listAll() = userDao.listAll()

    suspend fun get(id: String) = userDao.get(id)
}