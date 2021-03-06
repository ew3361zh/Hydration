package com.nikosnockoffs.android.hydration

import androidx.lifecycle.*
import kotlinx.coroutines.launch

// instance of waterrepo class
class WaterViewModel(private val waterRepository: WaterRepository, private val daysRepository: DaysRepository): ViewModel() {

    // alternative to writing this as a function below like the others
    // this can help monitor the data and notify the db
    // helpful if there is some data the app will need to access a lot
    val allRecords = waterRepository.getAllRecords().asLiveData()

    fun insertNewRecord(record: WaterRecord) { // this function called in the activity
                                               // will take care of background insert function
        viewModelScope.launch { // launch means do the below task in the background
                                // can run suspend functions from a launch lambda function
                                // viewModelScope means associate background function with this viewModel
                                // means viewmodel will track what bg tasks are running
                                // if viewmodel is closed/or activity it's related to, this task will be cancelled
                                // helps keep memory usage under control
            waterRepository.insert(record)
        }
    }

    fun updateRecord(record: WaterRecord) {
        viewModelScope.launch {
            waterRepository.update(record)
        }
    }

    fun deleteRecord(record: WaterRecord) {
        viewModelScope.launch {
            waterRepository.delete(record)
        }
    }

    fun getRecordForDay(day: String): LiveData<WaterRecord> {
        return waterRepository.getRecordForDay(day).asLiveData()
    }

//    fun getAllRecords(): LiveData<List<WaterRecord>> {
//        return repository.getAllRecords().asLiveData()
//    }

    fun getWeekdays(): List<String> {
        return daysRepository.weekdays
    }

    fun getTodayIndex(): Int {
        return daysRepository.todayIndex
    }

}

// viewModelProvider will be aware of this and be able to call it and make viewmodel objects in custom ways
class WaterViewModelFactory(private val waterRepository: WaterRepository, private val daysRepository: DaysRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterViewModel::class.java)) {
            return WaterViewModel(waterRepository, daysRepository) as T
        }
        throw IllegalArgumentException("$modelClass is not a WaterViewModel")
    }

}