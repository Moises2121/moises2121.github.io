package com.example.weighttracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    // Default values if no values are input from user
    private var currentWeight = 180.0

    private var goalWeight = 220.0
    private val username = "test"
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_history)

        db = DatabaseHelper(this)

        // load history when the History screen opens
        refreshHistory()

        // Add weight button
        findViewById<Button>(R.id.btnAddData).setOnClickListener {
            showAddWeightDialog()
        }

        // Dashboard button
        findViewById<Button>(R.id.btnHome).setOnClickListener {
            finish()
        }
    }

    // Load data from database and show the newest 5 rows
    private fun refreshHistory() {
        val history = db.getHistory(username)

        // newest 5 IDs in a list of rows
        val rowIds = listOf(R.id.row1, R.id.row2, R.id.row3, R.id.row4, R.id.row5)

        // Loop for 5 rows
        for (i in 0..4) {
            val row = findViewById<LinearLayout>(rowIds[i])

            // Each row has 3 views date, weight as text and Delete option button
            val dateText = row.getChildAt(0) as TextView
            val weightText = row.getChildAt(1) as TextView
            val deleteBtn = row.getChildAt(2) as Button

            if (i < history.size) {
                // If we have data for this row, show it
                val entry = history[i]
                dateText.text = entry.date
                weightText.text = "${entry.weight} lbs"
                deleteBtn.visibility = android.view.View.VISIBLE

                // after deleting a row, the entry will be deleted from database and screen refreshes
                deleteBtn.setOnClickListener {
                    db.deleteWeight(entry.id)
                    refreshHistory()
                }
            } else {
                // if there's no data in a row then show the empty row
                dateText.text = "-"
                weightText.text = "-"
                // hide delete button if no records in the row
                deleteBtn.visibility = android.view.View.INVISIBLE
            }
        }
    }

    // Show pop-up to enter current weight - Same functionality as button on Dashboard
    private fun showAddWeightDialog() {
        val input = EditText(this)
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this)
            .setTitle("Current Weight (lbs)")
            .setView(input)
            //save button
            .setPositiveButton("Save") { _, _ ->
                val text = input.text.toString()
                if (text.isNotEmpty()) {
                    currentWeight = text.toDouble()
                    // Saves the updated weight to database
                    DatabaseHelper(this).addWeight(username, currentWeight, goalWeight)
                    // check if the goal has been reach to send notification
                    if (currentWeight >= goalWeight) {
                        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
                        val enabled = prefs.getBoolean("notifications_enabled", true)
                        if (enabled) {
                            NotificationHelper.showGoalReached(this, goalWeight.toInt(), currentWeight.toInt())
                        }
                    }

                }
            }
            //cancel button
            .setNegativeButton("Cancel", null)
            .show()
    }
}