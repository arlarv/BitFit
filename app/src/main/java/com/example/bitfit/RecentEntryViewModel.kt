package com.example.bitfit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecentEntryViewModel : ViewModel() {
    private val _recentEntry = MutableLiveData<String>()
    val recentEntry: LiveData<String>
        get() = _recentEntry

    fun setRecentEntry(entryDetails: String) {
        _recentEntry.value = entryDetails
    }
}