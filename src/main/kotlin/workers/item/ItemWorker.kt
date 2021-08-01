package workers.item

import dao.ItemDao
import org.litote.kmongo.toId
import util.inject

class ItemWorker {
    private val dao: ItemDao by inject()

    suspend fun listAllByUser(userId: String) = dao.listAllByUser(userId.toId())

    suspend fun groupById(userId: String) = listAllByUser(userId).map { item -> item.id to item }.toMap()
}
