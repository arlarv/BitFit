package com.example.bitfit

import android.app.Application
import androidx.room.Room

class MyApplication : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app-database")
            .fallbackToDestructiveMigration()
            .build()
    }
}
