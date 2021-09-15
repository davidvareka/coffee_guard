package cz.olc.coffee.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import cz.olc.coffee.data.CoffeeType
import cz.olc.coffee.data.CoffeeTypeDao
import kotlinx.coroutines.flow.*

class CoffeeRepository(private val coffeeTypeDao: CoffeeTypeDao) {
    val coffeeTypes: Flow<List<CoffeeType>> = coffeeTypeDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(coffeeType: CoffeeType) {
        coffeeTypeDao.update(coffeeType)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun reset() {
        coffeeTypeDao.resetAll()
    }

}