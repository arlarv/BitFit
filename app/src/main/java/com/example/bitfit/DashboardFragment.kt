package com.example.bitfit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DashboardFragment : Fragment() {

    private lateinit var sleepViewModel: SleepViewModel
    private lateinit var totalHoursTextView: TextView
    private lateinit var averageRatingTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        totalHoursTextView = view.findViewById(R.id.textViewTotalHours)
        averageRatingTextView = view.findViewById(R.id.textViewAverageRating)
        val logSleepButton: Button = view.findViewById(R.id.button2) // Find the button by its ID

        // Initialize ViewModel
        sleepViewModel = ViewModelProvider(
            this,
            SleepViewModelFactory(requireActivity().application as MyApplication)
        ).get(SleepViewModel::class.java)

        // Get current week's start and end dates (Sunday to Saturday) in MM/DD/YYYY format
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.SUNDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        val startDate = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(calendar.time)

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        val endDate = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(calendar.time)



        sleepViewModel.getTotalHoursSleptInWeek(startDate, endDate).observe(viewLifecycleOwner, Observer { totalHours ->
            totalHoursTextView.text = "Total Hours Slept This Week: $totalHours"
        })


        sleepViewModel.getAverageRatingInWeek(startDate, endDate).observe(viewLifecycleOwner, Observer { averageRating ->
            averageRatingTextView.text = "Average Rating: $averageRating"
        })

        // Set an OnClickListener for the button
        logSleepButton.setOnClickListener {
            // Replace the current fragment with SleepEntryFragment
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, SleepEntryFragment())
            transaction.addToBackStack(null) // Optional: If you want to add the transaction to the back stack
            transaction.commit()
        }

        return view
    }

}
