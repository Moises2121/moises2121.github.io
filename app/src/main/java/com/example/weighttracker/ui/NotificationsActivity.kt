package com.example.weighttracker.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weighttracker.R
import androidx.core.content.edit
// Notification settings screen allowing the user to turn on/off the preferences
class NotificationsActivity : AppCompatActivity() {

    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_notifications)

        // Get username first so the greeting can be displayed too
        username = intent.getStringExtra("USERNAME") ?: run {
            Toast.makeText(this, getString(R.string.session_expired), Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Personalized greeting at the top to display current user
        findViewById<TextView>(R.id.tvWelcome).text = getString(R.string.welcome, username)

        val tvStatus = findViewById<TextView>(R.id.tvSmsStatus)
        val btnToggle = findViewById<Button>(R.id.btnEnableSms)
        val btnHome = findViewById<Button>(R.id.btnHome)

        val prefs = getSharedPreferences(getString(R.string.settings), MODE_PRIVATE)

        // SharedPreferences to store the On/OFF status locally
        fun updateUI() {
            val isOn = prefs.getBoolean(getString(R.string.notif_on), true)
            if (isOn) {
                tvStatus.text = getString(R.string.notif_on)
                btnToggle.text = getString(R.string.disable)
            } else {
                tvStatus.text = getString(R.string.notif_off)
                btnToggle.text = getString(R.string.enable)
            }
        }
        updateUI()

        // Toggle button ON/OFF
        btnToggle.setOnClickListener {
            val isOn = prefs.getBoolean(getString(R.string.notif_on), true)
            prefs.edit { putBoolean(getString(R.string.notif_on), !isOn).apply() }
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