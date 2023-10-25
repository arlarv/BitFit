package com.example.bitfit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SleepListFragment : Fragment() {

    private lateinit var sleepViewModel: SleepViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sleep_list, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val adapter = SleepEntryAdapter(emptyList()) // Initially, the adapter is empty
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize ViewModel
        sleepViewModel = ViewModelProvider(this, SleepViewModelFactory(requireActivity().application as MyApplication))
            .get(SleepViewModel::class.java)

        // Observe the LiveData from the ViewModel
        sleepViewModel.allEntries.observe(viewLifecycleOwner, Observer { entries ->
            adapter.setData(entries) // Update the adapter data when the LiveData changes
        })

        val logSleepButton: Button = view.findViewById(R.id.button)
        logSleepButton.setOnClickListener {
            // Replace the current fragment with SleepEntryFragment
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, SleepEntryFragment())
            //transaction.addToBackStack(null) // Optional: If you want to add the transaction to the back stack
            transaction.commit()
        }

        return view
    }
}
