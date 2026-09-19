package com.example.weighttracker.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weighttracker.R
import com.example.weighttracker.data.local.repository.WeightRepository

// Main Dashboard, shows the progress bar and CRUD operations
class DashboardActivity : AppCompatActivity() {

    private lateinit var username: String
    private var currentWeight = 180.0
    private var goalWeight = 220.0
    private lateinit var viewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_dashboard)

        // Gets username from login
        username = intent.getStringExtra("USERNAME")?: run {
            Toast.makeText(this, "Please login", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        // Personalized greeting at the top to display current user
        findViewById<TextView>(R.id.tvWelcome).text = "Welcome, $username"

        val repository = WeightRepository(applicationContext)

        // Sets up viewmodel which handles the data logic
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DashboardViewModel(repository) as T
            }
        })[DashboardViewModel::class.java]

        // Once history changes, the current weight and progress bar get updated
        viewModel.history.observe(this) { history ->
            if (history.isNotEmpty()) {
                currentWeight = history[0].weight
            }
            updateProgressBar()
        }

        // Once goal changes, the progress bar gets updated
        viewModel.goal.observe(this) { savedGoal ->
            goalWeight = savedGoal
            updateProgressBar()
        }

        // Initial database load according to current user
        viewModel.load(username)

        // Navigates to current user's goal screen
        findViewById<Button>(R.id.btnSetGoal).setOnClickListener {
            val intent = Intent(this, GoalActivity::class.java)
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        }

        // Navigates to add new weight entry into database
        findViewById<Button>(R.id.btnAddEdit).setOnClickListener {
            showAddWeightDialog()
        }

        // Navigates to the History screen
        findViewById<Button>(R.id.btnViewHistory).setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        }

        // Navigates to the Notifications screen
        findViewById<Button>(R.id.btnNotifications).setOnClickListener {
            val intent = Intent(this, NotificationsActivity::class.java)
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        }

        // Logout button, to end current user's session
        findViewById<Button>(R.id.btnHome).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
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
                    viewModel.addWeight(username, currentWeight, goalWeight)
                    // Checks if goal has been reached and calls notifications
                    if (currentWeight >= goalWeight) {
                        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
                        if (prefs.getBoolean("notifications_enabled", true)) {
                            NotificationHelper.showGoalReached(this, goalWeight.toInt(), currentWeight.toInt())
                        }
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Updates the progress bar using Linear Layout and shows percentage
    private fun updateProgressBar() {
        val tvGoal = findViewById<TextView>(R.id.tvGoalDisplay)
        val tvCurrent = findViewById<TextView>(R.id.tvCurrentDisplay)
        val tvPercent = findViewById<TextView>(R.id.tvProgressPercent)
        val container = findViewById<LinearLayout>(R.id.progressContainer)

        tvGoal.text = "CURRENT GOAL: ${goalWeight} lbs"
        tvCurrent.text = "Current Weight: ${currentWeight} lbs"

        // Calculation to get the percentage on the progress bar
        var progress = ((currentWeight / goalWeight) * 100).toInt()
        if (progress >= 100) {
            progress = 100
            tvPercent.text = "GOAL REACHED!"
            tvPercent.setTextColor(android.graphics.Color.WHITE)
        } else {
            if (progress < 0) progress = 0
            tvPercent.text = "$progress %"
        }
        // Adjusts the progress bar's color using Layout_weight
        val barParams = tvPercent.layoutParams as LinearLayout.LayoutParams
        barParams.weight = if (progress == 0) 0.01f else progress / 100f
        tvPercent.layoutParams = barParams
        if (container.childCount > 1) {
            val emptyBar = container.getChildAt(1)
            val emptyParams = emptyBar.layoutParams as LinearLayout.LayoutParams
            emptyParams.weight = (100 - progress) / 100f
            emptyBar.layoutParams = emptyParams
        }
    }

    // Reloads data after navigating through other screens, adjusting the model instantly
    override fun onResume() {
        super.onResume()
        viewModel.load(username)
    }
}