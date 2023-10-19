package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var sleepViewModel: SleepViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = SleepEntryAdapter(emptyList()) // Initially, the adapter is empty
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        sleepViewModel = ViewModelProvider(this, SleepViewModelFactory(application as MyApplication))
            .get(SleepViewModel::class.java)

        // Observe the LiveData from the ViewModel
        sleepViewModel.allEntries.observe(this, Observer { entries ->
            adapter.setData(entries) // Update the adapter data when the LiveData changes
        })

        val logSleepButton: Button = findViewById(R.id.button)
        logSleepButton.setOnClickListener {
            // When the button is clicked, start CreateEntryActivity
            val intent = Intent(this@MainActivity, CreateEntryActivity::class.java)
            startActivity(intent)
        }
    }
}
