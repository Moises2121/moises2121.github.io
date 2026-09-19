package com.example.weighttracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NotificationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_notifications)

        // find views
        val tvStatus = findViewById<TextView>(R.id.tvSmsStatus)
        val btnToggle = findViewById<Button>(R.id.btnEnableSms)
        val btnHome = findViewById<Button>(R.id.btnHome)

        // get saved setting
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)

        // update text based on current choice
        fun updateUI() {
            val isOn = prefs.getBoolean("notifications_enabled", true)
            if (isOn) {
                tvStatus.text = "Notifications: ON - We'll notify when you've reached your goal"
                btnToggle.text = "DISABLE SMS"
            } else {
                tvStatus.text = "Notifications: OFF"
                btnToggle.text = "ENABLE SMS"
            }
        }
        // show status first time
        updateUI()

        // toggle button
        btnToggle.setOnClickListener {
            val isOn = prefs.getBoolean("notifications_enabled", true)
            prefs.edit().putBoolean("notifications_enabled", !isOn).apply()
            updateUI() // refresh text
        }

        // navigate to main dashboard
        btnHome.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }
}