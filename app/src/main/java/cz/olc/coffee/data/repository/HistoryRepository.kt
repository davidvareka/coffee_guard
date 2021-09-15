package cz.olc.coffee.data.repository

import androidx.annotation.WorkerThread
import cz.olc.coffee.data.CoffeeType
import cz.olc.coffee.data.HistoryRecord
import cz.olc.coffee.data.HistoryRecordDao
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

class HistoryRepository(
    private val historyRecordDao: HistoryRecordDao
) {
    val historyRecords: Flow<List<HistoryRecord>> = historyRecordDao.getAll()
    val lastRecord: Flow<HistoryRecord> = historyRecordDao.getLast()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(
        record: HistoryRecord
    ) {
        historyRecordDao.insert(record)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clear() {
        historyRecordDao.deleteAll()
    }
}