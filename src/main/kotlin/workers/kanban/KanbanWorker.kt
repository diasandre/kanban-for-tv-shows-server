package workers.kanban

import util.inject
import workers.column.ColumnWorker
import workers.item.ItemWorker

class KanbanWorker {
    private val columnWorker: ColumnWorker by inject()
    private val itemWorker: ItemWorker by inject()

    companion object {
        const val COLUMNS = "columns"
        const val ITEMS = "items"
    }

    suspend fun build(userId: String) = run {
        val columns = columnWorker.listColumnsByUser(userId)
        val items = itemWorker.groupById(userId)

        val mapOfColumnsById = columns
            .map { column -> column.id to column }
            .toMap()

        mapOf(
            COLUMNS to mapOfColumnsById,
            ITEMS to items
        )
    }
}
