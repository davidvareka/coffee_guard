package cz.olc.coffee.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Entity(tableName = "coffee_type")
data class CoffeeType(
    @PrimaryKey val id: Int,
    val name: String,
    var order: Int,
    var count: Int = 0,
)

@Dao
interface CoffeeTypeDao {
    @Query("SELECT * FROM coffee_type")
    fun getAll(): Flow<List<CoffeeType>>

    @Query("SELECT * FROM coffee_type WHERE id = :id")
    fun loadById(id: Int): CoffeeType

    @Insert
    fun insert(coffeeType: CoffeeType)

    @Update
    fun update(coffeeType: CoffeeType)

    @Delete
    fun delete(coffeeType: CoffeeType)

    @Query("UPDATE coffee_type SET count = 0")
    suspend fun resetAll()

    @Query("DELETE FROM coffee_type")
    suspend fun deleteAll()
}
