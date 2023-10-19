package com.example.bitfit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_entries")
data class SleepEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "hours_of_sleep") val hoursOfSleep: Float,
    @ColumnInfo(name = "sleep_rating") val sleepRating: Float
)
