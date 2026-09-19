package com.example.weighttracker.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
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

    private var currentWeight = 180.0
    private var goalWeight = 220.0
    //Fixed the hardcoded username = "test" in this version 2.0 to accurately display current user's data
    private lateinit var username: String
    private lateinit var repository: WeightRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_history)

        // Get current user's username, otherwise session expires and send back to login screen
        username = intent.getStringExtra("USERNAME")?: run {
            Toast.makeText(this, "Session expired, please login", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        // Personalized greeting at the top to display current user
        findViewById<TextView>(R.id.tvWelcome).text = "Welcome, $username"

        repository = WeightRepository(applicationContext)
        refreshHistory()

        // Add new weight pop-up dialog
        findViewById<Button>(R.id.btnAddData).setOnClickListener { showAddWeightDialog() }
        // Go back to main dashboard
        findViewById<Button>(R.id.btnHome).setOnClickListener { finish() }
    }

    // Database is loaded and populates the latest five entries
    private fun refreshHistory() {
        lifecycleScope.launch {
            val history = repository.getHistory(username)
            val lastTarget = repository.getLastTarget(username)
            // Keep the latest goal if it exists
            if (lastTarget!= null && lastTarget > 0) {
                goalWeight = lastTarget
            }
            // Update current weight according to latest entry
            if (history.isNotEmpty()) {
                currentWeight = history[0].weight
            }

            // Row IDs for the five records displayed in the table
            val rowIds = listOf(R.id.row1, R.id.row2, R.id.row3, R.id.row4, R.id.row5)

            runOnUiThread {
                for (i in 0..4) {
                    val row = findViewById<LinearLayout>(rowIds[i])
                    // show date the record was captured
                    val dateText = row.getChildAt(0) as TextView
                    // Show weight captured
                    val weightText = row.getChildAt(1) as TextView
                    // Delete record by ID
                    val deleteBtn = row.getChildAt(2) as Button

                    if (i < history.size) {
                        val entry = history[i]
                        dateText.text = entry.date
                        weightText.text = "${entry.weight} lbs"
                        deleteBtn.visibility = View.VISIBLE
                        // Listener to delete specific record by ID
                        deleteBtn.setOnClickListener {
                            lifecycleScope.launch {
                                repository.deleteWeight(entry.id)
                                // Updates the history after deletion of row
                                refreshHistory()
                            }
                        }
                    } else {
                        // If no entries, -- placeholder is added in the three columns for each row
                        dateText.text = "-"
                        weightText.text = "-"
                        deleteBtn.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    // Pop-up dialog to enter current weight into the database, saving it against the current goal
    private fun showAddWeightDialog() {
        val input = EditText(this)
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        AlertDialog.Builder(this)
            .setTitle("Current Weight (lbs)")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val text = input.text.toString()
                if (text.isNotEmpty()) {
                    currentWeight = text.toDouble()
                    lifecycleScope.launch {
                        repository.addWeight(username, currentWeight, goalWeight)
                        // Updates history upon adding new weight
                        refreshHistory()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}