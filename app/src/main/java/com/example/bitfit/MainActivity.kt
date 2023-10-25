package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentContainer: Fragment
    private val recentEntryViewModel by lazy {
        ViewModelProvider(this).get(RecentEntryViewModel::class.java)
    }
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                fragmentContainer = SleepListFragment()
                loadFragment(fragmentContainer)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                fragmentContainer = DashboardFragment()
                loadFragment(fragmentContainer)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the default fragment (SleepListFragment) and load it
        fragmentContainer = SleepListFragment()
        loadFragment(fragmentContainer)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    // Function to load fragments into the fragment container
    private fun loadFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment is SleepListFragment) {
            finish() // Close the app when on SleepListFragment
        } else {
            super.onBackPressed() // Handle back navigation in other fragments normally
        }
    }
}