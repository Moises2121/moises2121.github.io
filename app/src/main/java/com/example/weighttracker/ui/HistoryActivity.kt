package com.example.weighttracker.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.weighttracker.R
import com.example.weighttracker.data.local.repository.WeightRepository
import kotlinx.coroutines.launch

// History screen, which shows five weight entries
class HistoryActivity : AppCompatActivity() {

    private var currentWeight = 0.0
    private var goalWeight = 0.0

    //Fixed the hardcoded username = "test" in this version 2 to accurately display current user's data
    private lateinit var username: String
    private lateinit var repository: WeightRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_history)

        // Get current user's username, otherwise session expires and send back to log in screen
        username = intent.getStringExtra("USERNAME") ?: run {
            Toast.makeText(this, getString(R.string.session_expired), Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        // Personalized greeting at the top to display current user
        findViewById<TextView>(R.id.tvWelcome).text = getString(R.string.welcome, username)

        repository = WeightRepository(applicationContext)
        refreshHistory()

        // Go back to Main Dashboard
        findViewById<Button>(R.id.btnHome).setOnClickListener { finish() }

    }

    // This method loads the database and populates the latest five entries in History screen
    private fun refreshHistory() {
        lifecycleScope.launch {
            // Build BST from Room data to display last 5 entries from current user
            val last5 = repository.getLastNDaysHistory(username, 5)
            // O(log n ) to get newest entry without scanning entire list
            val lastEntry = last5.lastOrNull()
            val lastTarget = repository.getLastTarget(username)
            // Keep the latest goal if it exists
            if (lastTarget!= null && lastTarget > 0) {
                goalWeight = lastTarget
            }
            if (lastEntry!= null) {
                currentWeight = lastEntry.weight
            }
            // Row IDs for the five records displayed in the table
            val rowIds = listOf(R.id.row5, R.id.row4, R.id.row3, R.id.row2, R.id.row1)

            for (i in 0..4) {
                val row = findViewById<LinearLayout>(rowIds[i])
                // Show date the record was captured
                val dateText = row.getChildAt(0) as TextView
                // Show weight captured
                val weightText = row.getChildAt(1) as TextView
                // Delete button for record by ID
                val deleteBtn = row.getChildAt(2) as Button

                if (i < last5.size) {
                    val entry = last5[i]
                    dateText.text = entry.date
                    weightText.text = "${entry.weight}"
                    deleteBtn.visibility = View.VISIBLE
                    // Listener to delete specific record by ID
                    deleteBtn.setOnClickListener {
                        confirmDelete(entry.id)
                    }
                } else {
                    // If no entries, "--" placeholder is added in the three columns for each row
                    dateText.text = "--"
                    weightText.text = "--"
                    deleteBtn.visibility = View.GONE
                    deleteBtn.setOnClickListener(null)
                }
            }
        }
    }

    // This helper method avoids creating a new setClickOnListener when history refreshes
    private fun confirmDelete(id: Int) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.delete_record))
            .setMessage(getString(R.string.are_you_sure))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                lifecycleScope.launch {
                    repository.deleteWeight(id)
                    refreshHistory()
                }
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }
}