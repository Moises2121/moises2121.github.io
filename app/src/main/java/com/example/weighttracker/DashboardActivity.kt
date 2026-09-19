package com.example.weighttracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    // Default values if no values are input from user
    private var currentWeight = 180.0
    private var goalWeight = 220.0
    private val username = "test"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_dashboard)

        val db = DatabaseHelper(this)

        // Load goal from database
        val savedGoal = db.getLastTarget(username)
        if (savedGoal > 0) {
            goalWeight = savedGoal
        }

        // Then load current weight from history
        loadCurrentWeight()

        // --- DASHBOARD BUTTONS --- //

        // Go to SCREEN GOAL screen
        findViewById<Button>(R.id.btnSetGoal).setOnClickListener {
            startActivity(Intent(this, GoalActivity::class.java))
        }

        // Update current weight
        findViewById<Button>(R.id.btnAddEdit).setOnClickListener {
            showAddWeightDialog()
        }

        // Go back to main dashboard
        findViewById<Button>(R.id.btnHome).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // View weight history showing latest 5 results
        findViewById<Button>(R.id.btnViewHistory).setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        // Go to SCREEN NOTIFICATIONS screen
        findViewById<Button>(R.id.btnNotifications).setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }
    }

    // Show pop-up to enter current weight
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

                    // Refresh the progress bar
                    updateProgressBar()
                }
            }
            //cancel button
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Get up-to-date weight from database
    private fun loadCurrentWeight() {
        try {
            val db = DatabaseHelper(this)
            val history = db.getHistory(username)
            if (history.isNotEmpty()) {
                currentWeight = history[0].weight
            }
        // ignore error if database has no entries
        } catch (e: Exception) {

        }
        updateProgressBar()
    }

    // Update the texts and progress bar
    private fun updateProgressBar() {
        //Maintain tv prefix for better references
        val tvGoal = findViewById<TextView>(R.id.tvGoalDisplay)
        val tvCurrent = findViewById<TextView>(R.id.tvCurrentDisplay)
        val tvPercent = findViewById<TextView>(R.id.tvProgressPercent)
        val container = findViewById<LinearLayout>(R.id.progressContainer)

        // Show goal and current
        tvGoal.text = "CURRENT GOAL: ${goalWeight} lbs"
        tvCurrent.text = "Current Weight: ${currentWeight} lbs"

        // Calculate the progress: current / (goal * 100)
        var progress = ((currentWeight / goalWeight) * 100).toInt()

        // Limit set to 100%
        if (progress >= 100) {
            progress = 100
            // display message when reaching 100%
            tvPercent.text = "GOAL REACHED !"
            tvPercent.setTextColor(android.graphics.Color.WHITE)
        } else {
            // Make sure progress is 0 to 100 only
            if (progress < 0) progress = 0
            if (progress > 100) progress = 100
            tvPercent.text = "$progress %"
        }

        // Update the colored bar width
        val barParams = tvPercent.layoutParams as LinearLayout.LayoutParams
        barParams.weight = if (progress == 0) 0.01f else progress / 100f
        tvPercent.layoutParams = barParams

        // Update the empty part of bar
        if (container.childCount > 1) {
            val emptyBar = container.getChildAt(1)
            val emptyParams = emptyBar.layoutParams as LinearLayout.LayoutParams
            emptyParams.weight = (100 - progress) / 100f
            emptyBar.layoutParams = emptyParams
        }
    }
    // Refresh data after switching windows
    override fun onResume() {
        super.onResume()
        val newGoal = DatabaseHelper(this).getLastTarget(username)
        if (newGoal > 0) {
            goalWeight = newGoal
        }
        loadCurrentWeight()
    }
}