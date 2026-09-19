package com.example.weighttracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GoalActivity : AppCompatActivity() {

    private val username = "test"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_goal)

        val db = DatabaseHelper(this)

        // Find views from screen goal XML
        val etGoal = findViewById<EditText>(R.id.editTargetWeight)
        val tvConfirmation = findViewById<TextView>(R.id.tvGoalConfirmation)
        val btnSave = findViewById<Button>(R.id.btnSaveGoal)
        val btnBack = findViewById<Button>(R.id.btnBackGoal)

        // show last saved goal when screen opens
        showLastGoal(db, tvConfirmation)

        // save new goal to database
        btnSave.setOnClickListener {
            val goalText = etGoal.text.toString()

            if (goalText.isEmpty()) {
                // if empty, show error
                Toast.makeText(this, "Enter your Goal", Toast.LENGTH_SHORT).show()
            } else {
                val goal = goalText.toDouble()

                // Save goal to database
                db.addWeight(username, 0.0, goal)

                // Update the text on screen
                tvConfirmation.text = "Your goal is Set to ${goal} lbs!"

                // Clear the input box
                etGoal.text.clear()
            }
        }

        // go back to main dashboard
        btnBack.setOnClickListener {
            finish()
        }
    }

    // Helper to show last goal
    private fun showLastGoal(db: DatabaseHelper, tv: TextView) {
        val lastGoal = db.getLastTarget(username)
        if (lastGoal > 0) {
            tv.text = "Your goal is set to ${lastGoal} lbs!"
        } else {}

    }
}