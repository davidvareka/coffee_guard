package cz.olc.coffee.data.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.olc.coffee.data.CoffeeType
import cz.olc.coffee.data.CoffeeTypeDao
import cz.olc.coffee.data.HistoryRecord
import cz.olc.coffee.data.HistoryRecordDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [CoffeeType::class, HistoryRecord::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coffeeTypeDao(): CoffeeTypeDao
    abstract fun historyRecordDao(): HistoryRecordDao

    private class DatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val coffeeTypeDao = database.coffeeTypeDao()

                    // Delete all content here.
                    coffeeTypeDao.deleteAll()

                    val types = listOf(
                        "Espresso",
                        "Espresso\n s mlékem",
                        "Lungo\n s mlékem",
                        "Mochaccino",
                        "Caffe Latte",
                        "Čokoláda",
                    )

                    types.forEachIndexed { index, type ->
                        coffeeTypeDao.insert(
                            CoffeeType(index, type, index)
                        )
                    }
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "Coffeeguard"
                )
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}