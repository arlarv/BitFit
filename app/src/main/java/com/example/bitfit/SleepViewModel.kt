package com.example.bitfit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SleepViewModel(private val application: MyApplication) : ViewModel() {
    private val sleepDAO = application.database.sleepEntryDao()
    val allEntries = sleepDAO.getAllEntries()

    // Function to get total hours slept in the given week
    fun getTotalHoursSleptInWeek(startDate: String, endDate: String): LiveData<Float> {
        val result = sleepDAO.getTotalHoursSleptInWeek(startDate, endDate)
        Log.d("ViewModel", "Total Hours Slept: $result")
        return result
    }

    // Function to get average rating in the given week
    fun getAverageRatingInWeek(startDate: String, endDate: String): LiveData<Float> {
        val result = sleepDAO.getAverageRatingInWeek(startDate, endDate)
        Log.d("ViewModel", "Average Rating: $result")
        return result
    }
    // Function to insert a new sleep entry
    fun insert(entry: SleepEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            sleepDAO.insert(entry)
        }
    }
}

class SleepViewModelFactory(private val application: MyApplication) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepViewModel::class.java)) {
            return SleepViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
