package cz.olc.coffee.ui.models

import androidx.lifecycle.*
import cz.olc.coffee.data.CoffeeType
import cz.olc.coffee.data.HistoryRecord
import cz.olc.coffee.data.repository.CoffeeRepository
import cz.olc.coffee.data.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    private val typeRepository: CoffeeRepository,
    private val historyRepository: HistoryRepository,
) : ViewModel() {
    val coffeeTypes = typeRepository.coffeeTypes.asLiveData()
    val coffeePrice = MutableLiveData(5)
    val lastRecord = historyRepository.lastRecord.asLiveData()

    fun storeCoffee(coffeeType: CoffeeType) = viewModelScope.launch(Dispatchers.IO) {
        coffeeType.count++
        typeRepository.update(coffeeType)

        val calendar = Calendar.getInstance()
        val df = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formatDate: String = df.format(calendar.time)

        val record = HistoryRecord(
            0,
            coffeeType.id,
            coffeeType.name,
            formatDate,
            coffeePrice.value!!
        )

        historyRepository.insert(record)
    }

    fun resetCounter() {
        viewModelScope.launch(Dispatchers.IO) {
            typeRepository.reset()
        }
    }
}

class MainViewModelFactory(
    private val repository: CoffeeRepository,
    private val historyRepository: HistoryRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository, historyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
