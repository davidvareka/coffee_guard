package cz.olc.coffee.ui.models

import androidx.lifecycle.*
import cz.olc.coffee.data.CoffeeType
import cz.olc.coffee.data.repository.CoffeeRepository
import cz.olc.coffee.data.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyRepository: HistoryRepository,
    ) : ViewModel() {
    val history = historyRepository.historyRecords.asLiveData()

    fun clear() {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.clear()
        }
    }
}

class HistoryViewModelFactory(private val historyRepository: HistoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(historyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
