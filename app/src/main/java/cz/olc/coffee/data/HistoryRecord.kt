package cz.olc.coffee.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "history")
data class HistoryRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val coffeeTypeId: Int,
    val coffeeTypeName: String,
    val date: String,
    val price: Int,
)

@Dao
interface HistoryRecordDao {
    @Query("SELECT id, coffeeTypeId, GROUP_CONCAT(coffeeTypeName, '|') coffeeTypeName, date, price FROM history GROUP BY date ORDER BY id DESC LIMIT 30")
    fun getAll(): Flow<List<HistoryRecord>>

    @Query("SELECT * FROM history ORDER BY id DESC LIMIT 1")
    fun getLast(): Flow<HistoryRecord>

    @Insert
    fun insert(historyRecord: HistoryRecord)

    @Delete
    fun delete(historyRecord: HistoryRecord)

    @Query("DELETE FROM history")
    suspend fun deleteAll()
}