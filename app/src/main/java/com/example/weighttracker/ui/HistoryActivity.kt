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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        // Get current user's username, otherwise session expires and send back to log in screen
        username = intent.getStringExtra("USERNAME") ?: run {
            Toast.makeText(this, "Session expired, please log in", Toast.LENGTH_SHORT).show()
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
            // Build BST from Room data
            val last5 = repository.getLastNDaysHistory(username, 5)
            val lastEntry = repository.getLastEntry(username)
            val lastTarget = repository.getLastTarget(username)
            // Keep the latest goal if it exists
            if (lastTarget != null && lastTarget > 0) {
                goalWeight = lastTarget
            }

            // O(log n ) to get newest entry without scanning entire list
            if (lastEntry != null) {
                currentWeight = lastEntry.weight
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

                    if (i < last5.size) {
                        val entry = last5[i]
                        dateText.text = entry.date
                        weightText.text = "${entry.weight} lbs"
                        deleteBtn.visibility = View.VISIBLE
                        // Listener to delete specific record by ID
                        deleteBtn.setOnClickListener {
                            // Added confirmation screen for version 2
                            AlertDialog.Builder(it.context)
                                .setTitle("Delete Record")
                                .setMessage("Are you sure you want to delete?")
                                .setPositiveButton("Yes") { _, _ ->
                                    lifecycleScope.launch {
                                        repository.deleteWeight(entry.id)
                                        // Updates the history after deletion of row
                                        refreshHistory()
                                    }
                                }
                                .setNegativeButton("NO", null)
                                .show()
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
        lifecycleScope.launch {
            val today = SimpleDateFormat("MM/dd/yyyy", Locale.US).format(Date())
            val lastEntry = repository.getLastEntry(username)
            val newestDate = lastEntry?.date

            // Restrict multiple entries on the same day
            if (newestDate == today) {
                AlertDialog.Builder(this@HistoryActivity)
                    .setTitle("Oops!")
                    .setMessage("You already logged your weight today ($today). Come back tomorrow!")
                    .setPositiveButton("OK", null)
                    .show()
                return@launch
            }

            val input = EditText(this@HistoryActivity)
            input.inputType =
                android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
                AlertDialog.Builder(this@HistoryActivity)
                    .setTitle("Current Weight (lbs)")
                    .setView(input)
                    .setPositiveButton("Save") { _, _ ->
                        val text = input.text.toString()
                        if (text.isNotEmpty()) {
                            val newWeight = text.toDouble()
                            currentWeight = newWeight
                            lifecycleScope.launch {
                                repository.addWeight(username, newWeight, goalWeight)

                            // Use new entry directly as O(1) instead of O(log n) database query
                            val newEntry = com.example.weighttracker.data.local.WeightEntry(
                                username = username,
                                weight = newWeight,
                                date = today,
                                goalWeight = goalWeight
                            )
                            val prefs = getSharedPreferences("settings", MODE_PRIVATE)
                                if (prefs.getBoolean("notifications_enabled", true)) {
                                // NotificationHelper gets WeightEntry directly as O(log n)
                                NotificationHelper.showGoalReachedIfNeeded(
                                    this@HistoryActivity,
                                    newEntry,
                                    goalWeight.toInt()
                                )
                            }
                            // Updates history upon adding new weight
                            refreshHistory()
                        }
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}