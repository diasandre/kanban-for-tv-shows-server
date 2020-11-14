package workers.item

import dao.ItemDao
import org.litote.kmongo.id.StringId
import util.inject

class ItemWorker {
    private val dao: ItemDao by inject()

    suspend fun listAllByUser(userId: String) = dao.listAllByUser(StringId(userId))
}