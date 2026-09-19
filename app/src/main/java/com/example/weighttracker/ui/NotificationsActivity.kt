package com.example.weighttracker.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weighttracker.R

// Notification settings screen allowing the user to turn on/off the preferences
class NotificationsActivity : AppCompatActivity() {

    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_notifications)

        // Get username first so the greeting can be displayed too
        username = intent.getStringExtra("USERNAME") ?: run {
            Toast.makeText(this, "Session expired", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Personalized greeting at the top to display current user
        findViewById<TextView>(R.id.tvWelcome).text = "Welcome, $username"

        val tvStatus = findViewById<TextView>(R.id.tvSmsStatus)
        val btnToggle = findViewById<Button>(R.id.btnEnableSms)
        val btnHome = findViewById<Button>(R.id.btnHome)

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)

        // SharedPreferences to store the On/OFF status locally
        fun updateUI() {
            val isOn = prefs.getBoolean("notifications_enabled", true)
            if (isOn) {
                tvStatus.text = "Notifications: ON - We'll notify when you've reached your goal"
                btnToggle.text = "DISABLE"
            } else {
                tvStatus.text = "Notifications: OFF"
                btnToggle.text = "ENABLE"
            }
        }
        updateUI()

        // Toggle button ON/OFF
        btnToggle.setOnClickListener {
            val isOn = prefs.getBoolean("notifications_enabled", true)
            prefs.edit().putBoolean("notifications_enabled", !isOn).apply()
            updateUI()
        }

        // Button to go back to main dashboard
        btnHome.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.putExtra("USERNAME", username)
            startActivity(intent)
            finish()
        }
    }
}