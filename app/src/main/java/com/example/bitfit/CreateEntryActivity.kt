package com.example.bitfit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RatingBar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateEntryActivity : AppCompatActivity() {

    private lateinit var datePicker: DatePicker
    private lateinit var hoursOfSleepEditText: EditText
    private lateinit var ratingBarSleepRating: RatingBar
    private lateinit var buttonSaveEntry: Button
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_entry)

        // Initialize Room database
        database = (application as MyApplication).database
        val sleepDAO = database.sleepEntryDao()

        // Initialize UI components
        datePicker = findViewById(R.id.datePicker)
        hoursOfSleepEditText = findViewById(R.id.editTextHoursOfSleep)
        ratingBarSleepRating = findViewById(R.id.ratingBarSleepRating)
        buttonSaveEntry = findViewById(R.id.buttonSaveEntry)

        // Set up click listener for Save Entry button
        buttonSaveEntry.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            val formattedDate = formatDate(calendar)
            val hoursOfSleepText = hoursOfSleepEditText.text.toString().trim()

            if (hoursOfSleepText.isEmpty() || hoursOfSleepText.toFloat() <= 0 || hoursOfSleepText.toFloat() >= 25 ) {
                // If hours of sleep is empty or not a valid positive number, show an error message
                hoursOfSleepEditText.error = "Please enter a valid number of hours"
            } else {
                // Hours of sleep is valid, proceed with saving the entry
                val hoursOfSleep = hoursOfSleepText.toFloat()
                val sleepRating = ratingBarSleepRating.rating
                val entry = SleepEntry(date = formattedDate, hoursOfSleep = hoursOfSleep, sleepRating = sleepRating)

                // Perform database operation on a background thread using Thread
                Thread {
                    sleepDAO.insert(entry)
                }.start()

                finish() // Close the activity after saving the entry
            }
        }
    }

    private fun formatDate(calendar: Calendar): String {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}
