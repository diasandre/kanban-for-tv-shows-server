package workers.column

import dao.ColumnDao
import models.Column
import models.ColumnDTO
import org.litote.kmongo.toId
import util.defaultColumns
import util.inject

class ColumnWorker {
    private val dao: ColumnDao by inject()

    suspend fun createDefaultColumns(id: String) = defaultColumns(id.toId())
        .toColumn()
        .saveAndReturnKeys()

    private fun List<ColumnDTO>.toColumn() = this.map(::Column)

    private suspend fun List<Column>.saveAndReturnKeys() = run {
        dao.saveAll(this)
        map(Column::_id)
    }
}