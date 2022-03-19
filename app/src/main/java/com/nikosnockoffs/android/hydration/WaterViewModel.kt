package com.nikosnockoffs.android.hydration

import androidx.lifecycle.*
import kotlinx.coroutines.launch

// instance of waterrepo class
class WaterViewModel(private val repository: WaterRepository): ViewModel() {

    // alternative to writing this as a function below like the others
    // this can help monitor the data and notify the db
    // helpful if there is some data the app will need to access a lot
    val allRecords = repository.getAllRecords().asLiveData()

    fun insertNewRecord(record: WaterRecord) { // this function called in the activity
                                               // will take care of background insert function
        viewModelScope.launch { // launch means do the below task in the background
                                // can run suspend functions from a launch lambda function
                                // viewModelScope means associate background function with this viewModel
                                // means viewmodel will track what bg tasks are running
                                // if viewmodel is closed/or activity it's related to, this task will be cancelled
                                // helps keep memory usage under control
            repository.insert(record)
        }
    }

    fun updateRecord(record: WaterRecord) {
        viewModelScope.launch {
            repository.update(record)
        }
    }

    fun deleteRecord(record: WaterRecord) {
        viewModelScope.launch {
            repository.delete(record)
        }
    }

    fun getRecordForDay(day: String): LiveData<WaterRecord> {
        return repository.getRecordForDay(day).asLiveData()
    }

//    fun getAllRecords(): LiveData<List<WaterRecord>> {
//        return repository.getAllRecords().asLiveData()
//    }

}

// viewModelProvider will be aware of this and be able to call it and make viewmodel objects in custom ways
class WaterViewModelFactory(private val repository: WaterRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterViewModel::class.java)) {
            return WaterViewModel(repository) as T
        }
        throw IllegalArgumentException("$modelClass is not a WaterViewModel")
    }

}