package com.example.weighttracker.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.weighttracker.R
import com.example.weighttracker.data.local.repository.WeightRepository
import kotlinx.coroutines.launch

// Goal Screen where users view and update the weight's goal
class GoalActivity : AppCompatActivity() {

    //Fixed the hardcoded username = "test" in this version 2.0 to accurately display current user's data
    private lateinit var username: String
    private lateinit var viewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_goal)

        // Get username, upon failure, the session expires and sends back to login screen
        username = intent.getStringExtra("USERNAME") ?: run {
            Toast.makeText(this, "Session expired, please login", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Personalized greeting at the top to display current user
        findViewById<TextView>(R.id.tvWelcome).text = "Welcome, $username"

        // Viewmodel with repository that access the database
        val repo = WeightRepository(applicationContext)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DashboardViewModel(repo) as T
            }
        })[DashboardViewModel::class.java]

        // Input field for the new goal
        val etGoal = findViewById<EditText>(R.id.editTargetWeight)
        // Confirmation TextView upon adding new goal
        val tvConfirmation = findViewById<TextView>(R.id.tvGoalConfirmation)
        // Save button to save goal into database
        val btnSave = findViewById<Button>(R.id.btnSaveGoal)
        // Dashboard (back) button
        val btnBack = findViewById<Button>(R.id.btnBackGoal)

        viewModel.goal.observe(this) { lastGoal ->
            if (lastGoal > 0) {
                tvConfirmation.text = "Your goal is set to ${lastGoal} lbs!"
            }
        }

        // Loads user from database upon success
        viewModel.load(username)

        // Button to save new goal and reuse current weight to fix it from disapearing
        btnSave.setOnClickListener {
            val goalText = etGoal.text.toString()
            if (goalText.isEmpty()) {
                Toast.makeText(this, "Enter your Goal", Toast.LENGTH_SHORT).show()
            } else {
                val newGoal = goalText.toDouble()
                lifecycleScope.launch {
                    val history = repo.getHistory(username)
                    val currentWeight = history.firstOrNull()?.weight ?: 140.0
                    viewModel.addWeight(username, currentWeight, newGoal)
                    runOnUiThread {
                        // Confirmation upon adding new goal
                        tvConfirmation.text = "Your goal is Set to ${newGoal} lbs!"
                        etGoal.text.clear()
                        Toast.makeText(this@GoalActivity, "Goal saved!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        // Send current user to the previous screen which is the main dashboard
        btnBack.setOnClickListener { finish() }
    }
}