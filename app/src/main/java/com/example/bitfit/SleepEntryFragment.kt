package com.example.bitfit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SleepEntryFragment : Fragment() {

    private lateinit var hoursOfSleepEditText: EditText
    private lateinit var ratingBarSleepRating: RatingBar
    private lateinit var datePicker: DatePicker
    private lateinit var buttonSaveEntry: Button
    private lateinit var database: AppDatabase
    private lateinit var textViewEntryDetails: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sleep_entry, container, false)

        // Initialize UI components
        hoursOfSleepEditText = view.findViewById(R.id.editTextHoursOfSleep)
        ratingBarSleepRating = view.findViewById(R.id.ratingBarSleepRating)
        datePicker = view.findViewById(R.id.datePicker)
        buttonSaveEntry = view.findViewById(R.id.buttonSaveEntry)
        textViewEntryDetails = view.findViewById(R.id.textViewEntryDetails)

        // Initialize Room database
        database = (requireActivity().application as MyApplication).database
        val sleepDAO = database.sleepEntryDao()

        // Load most recent entry details and display them
        loadMostRecentEntry(sleepDAO)

        // Set click listener for the save button
        buttonSaveEntry.setOnClickListener {
            // Validate input fields
            val hoursOfSleepText = hoursOfSleepEditText.text.toString().trim()
            val sleepRating = ratingBarSleepRating.rating
            val calendar = Calendar.getInstance()
            calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            val formattedDate = formatDate(calendar)

            if (hoursOfSleepText.isEmpty() || hoursOfSleepText.toFloat() <= 0 || hoursOfSleepText.toFloat() >= 25) {
                // Handle invalid input for hours of sleep (show error message, etc.)
                hoursOfSleepEditText.error = "Please enter a valid number of hours"
            } else if (sleepRating == 0f) {
                // Handle invalid input for sleep rating (show error message, etc.)
                showToast("Please select a sleep rating")
            } else {
                // Valid input, save the entry to the database using CoroutineScope
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val hoursOfSleep = hoursOfSleepText.toFloat()
                        val entry = SleepEntry(date = formattedDate, hoursOfSleep = hoursOfSleep, sleepRating = sleepRating)
                        sleepDAO.insert(entry)

                        // Show success message to the user on the main thread
                        activity?.runOnUiThread {
                            showToast("Sleep logged successfully!")

                            // Reset fields to show that the user can add another entry if wanted
                            hoursOfSleepEditText.text.clear()
                            ratingBarSleepRating.rating = 0f

                            // Navigate back to SleepListFragment after successful entry
                            navigateToSleepListFragment()
                        }
                    } catch (e: Exception) {
                        // Handle database insertion failure
                        // Example: Log.e("SleepEntryFragment", "Failed to add entry", e)
                    }
                }

                // Update entry details text view on the main thread
                activity?.runOnUiThread {
                    textViewEntryDetails.text = buildRecentEntryDetails(formattedDate, hoursOfSleepText.toFloat(), sleepRating)
                }
            }
        }

        return view
    }

    private fun loadMostRecentEntry(sleepDAO: SleepDAO) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val recentEntry = sleepDAO.getMostRecentEntry()
                if (recentEntry != null) {
                    activity?.runOnUiThread {
                        textViewEntryDetails.text = buildRecentEntryDetails(
                            recentEntry.date,
                            recentEntry.hoursOfSleep,
                            recentEntry.sleepRating
                        )
                    }
                }
            } catch (e: Exception) {
                // Handle database query failure
                // Example: Log.e("SleepEntryFragment", "Failed to fetch most recent entry", e)
            }
        }
    }

    private fun navigateToSleepListFragment() {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, SleepListFragment())
        transaction.addToBackStack(null) // Add the transaction to the back stack
        transaction.commit()
    }

    private fun formatDate(calendar: Calendar): String {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun showToast(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun buildRecentEntryDetails(date: String, hoursSlept: Float, sleepRating: Float): String {
        return "Date: $date\nHours Slept: $hoursSlept\nSleep Rating: ${sleepRating.toInt()}"
    }
}
