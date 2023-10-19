package com.example.bitfit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.R
import com.example.bitfit.SleepEntry

class SleepEntryAdapter(private var entries: List<SleepEntry> = emptyList()) : RecyclerView.Adapter<SleepEntryAdapter.SleepEntryViewHolder>() {

    // Function to update the data and refresh the adapter
    fun setData(entries: List<SleepEntry>) {
        this.entries = entries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepEntryViewHolder {
        // Inflate the layout for each item in the RecyclerView
        val view = LayoutInflater.from(parent.context).inflate(R.layout.log_entry, parent, false)
        return SleepEntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SleepEntryViewHolder, position: Int) {
        // Bind data to the views inside each item of the RecyclerView
        val entry = entries[position]
        holder.dateTextView.text = entry.date.toString() // Format this according to your needs
        holder.hoursTextView.text = "Hours slept: " + entry.hoursOfSleep.toString() // Format this according to your needs
        holder.ratingTextView.text = "Sleep Rating: " + entry.sleepRating.toString() // Format this according to your needs
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    // ViewHolder class to hold the views for each item
    class SleepEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.sleepDate) // Replace with your TextView IDs
        val hoursTextView: TextView = itemView.findViewById(R.id.hoursSlept) // Replace with your TextView IDs
        val ratingTextView: TextView = itemView.findViewById(R.id.sleepQuality) // Replace with your TextView IDs
    }
}
