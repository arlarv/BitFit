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

    @Query("SELECT * FROM sleep_entries ORDER BY id DESC")
    fun getAllEntries(): LiveData<List<SleepEntry>>

    @Query("SELECT * FROM sleep_entries ORDER BY id DESC LIMIT 1")
    fun getMostRecentEntry(): SleepEntry

    @Query("SELECT SUM(hours_of_sleep) FROM sleep_entries WHERE date BETWEEN :startDate AND :endDate")
    fun getTotalHoursSleptInWeek(startDate: String, endDate: String): LiveData<Float>

    @Query("SELECT AVG(sleep_rating) FROM sleep_entries WHERE date BETWEEN :startDate AND :endDate")
    fun getAverageRatingInWeek(startDate: String, endDate: String): LiveData<Float>
}
