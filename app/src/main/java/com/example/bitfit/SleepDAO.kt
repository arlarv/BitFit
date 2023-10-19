package com.example.bitfit

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SleepDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entry: SleepEntry)

    @Query("SELECT * FROM sleep_entries ORDER BY id ASC")
    fun getAllEntries(): LiveData<List<SleepEntry>>
}
