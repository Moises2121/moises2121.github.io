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
import androidx.lifecycle.lifecycleScope
import com.example.weighttracker.R
import com.example.weighttracker.data.local.repository.WeightRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.os.Build

// Main Dashboard, shows the progress bar and CRUD operations
class DashboardActivity : AppCompatActivity() {

    private lateinit var username: String
    private var currentWeight = 0.0
    private var goalWeight = 0.0
    private lateinit var viewModel: DashboardViewModel
    private lateinit var repository: WeightRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_dashboard)
        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
        }

        // Gets username from login
        username = intent.getStringExtra("USERNAME") ?: run {
            Toast.makeText(this, getString(R.string.please_login), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        // Personalized greeting at the top to display current user
        findViewById<TextView>(R.id.tvWelcome).text = getString(R.string.welcome, username)

        repository = WeightRepository(applicationContext)

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
                // History from getLastNDays sorted by BST
                currentWeight = history.last().weight
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

        // Logout button, to end current user's session + Goodbye Message for current user
        findViewById<Button>(R.id.btnHome).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("GOODBYE_MSG", getString(R.string.goodbye, username))
            startActivity(intent)
            finish()
        }
    }

    // Pop-up dialog to enter current weight into the database, saving it against the current goal
    private fun showAddWeightDialog() {
        lifecycleScope.launch {
            val today = SimpleDateFormat("MM/dd/yyyy", Locale.US).format(Date())
            val lastEntry = repository.getLastEntry(username)

            // Restrict multiple entries on the same day
            if (lastEntry?.date == today) {
                AlertDialog.Builder(this@DashboardActivity)
                    .setTitle(getString(R.string.oops))
                    .setMessage(getString(R.string.already_logged, today))
                    .setPositiveButton(getString(R.string.ok), null)
                    .show()
                return@launch
            }
            val input = EditText(this@DashboardActivity)
            input.inputType =
                android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
                AlertDialog.Builder(this@DashboardActivity)
                .setTitle(getString(R.string.current_weight_lbs))
                .setView(input)
                .setPositiveButton(getString(R.string.save)) { _, _ ->
                    val text = input.text.toString()
                    if (text.isNotEmpty()) {
                        val newWeight = text.toDouble()
                        currentWeight = newWeight
                        viewModel.addWeight(username, newWeight, goalWeight)

                        // Create entry for the notification check
                        val newEntry = com.example.weighttracker.data.local.WeightEntry(
                            username = username,
                            weight = newWeight,
                            date = SimpleDateFormat("MM/dd/yyyy", Locale.US).format(Date()),
                            goalWeight = goalWeight
                        )
                        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
                        prefs.getBoolean("notifications_enabled", true)
                        if (prefs.getBoolean(getString(R.string.notif_on), true)) {
                            // Check if goal has been reached to trigger notification
                            NotificationHelper.showGoalReachedIfNeeded(
                                this@DashboardActivity,
                                newEntry,
                                goalWeight.toInt()
                            )
                        }
                    }
                }
                .setNegativeButton(getString(R.string.cancel), null)
                .show()
        }
    }

        // Updates the progress bar using Linear Layout and shows percentage
        private fun updateProgressBar() {
            val tvGoal = findViewById<TextView>(R.id.tvGoalDisplay)
            val tvCurrent = findViewById<TextView>(R.id.tvCurrentDisplay)
            val tvPercent = findViewById<TextView>(R.id.tvProgressPercent)
            val container = findViewById<LinearLayout>(R.id.progressContainer)

            tvGoal.text = getString(R.string.current_goal, goalWeight)
            tvCurrent.text = getString(R.string.current_weight, currentWeight)

            // Calculation to get the percentage on the progress bar
            var progress = if (goalWeight > 0) {
                ((currentWeight / goalWeight) * 100).toInt()
            } else {
                0
            }
            if (progress >= 100) {
                progress = 100
                tvPercent.text = getString(R.string.goal_reached)
                tvPercent.setTextColor(android.graphics.Color.WHITE)
            } else {
                if (progress < 0) progress = 0
                tvPercent.text = getString(R.string.progress, progress)
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