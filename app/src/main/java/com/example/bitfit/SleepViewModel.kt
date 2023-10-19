package com.example.bitfit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SleepViewModel(private val application: MyApplication) : ViewModel() {
    private val sleepDAO = application.database.sleepEntryDao()
    val allEntries = sleepDAO.getAllEntries()
}

class SleepViewModelFactory(private val application: MyApplication) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepViewModel::class.java)) {
            return SleepViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
