package cz.olc.coffee

import android.app.Application
import cz.olc.coffee.data.repository.AppDatabase
import cz.olc.coffee.data.repository.CoffeeRepository
import cz.olc.coffee.data.repository.HistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CoffeeGuardApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val typeRepository by lazy { CoffeeRepository(database.coffeeTypeDao()) }
    val historyRepository by lazy { HistoryRepository(database.historyRecordDao()) }
}