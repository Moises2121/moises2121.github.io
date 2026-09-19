package com.example.weighttracker.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.weighttracker.R
import com.example.weighttracker.data.local.repository.UserRepository
import kotlinx.coroutines.launch

// Main screen for login and registration, handling user authentication before access to the dashboard
class MainActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userRepository = UserRepository(this)

        // Login fields
        val editLoginUser = findViewById<EditText>(R.id.editLoginUser)
        val editLoginPass = findViewById<EditText>(R.id.editLoginPass)
        // New user registration fields
        val editRegUser = findViewById<EditText>(R.id.editRegUser)
        val editRegPass = findViewById<EditText>(R.id.editRegPass)
        // Login and Register buttons
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // Registers new user and checks if the user already exists
        btnRegister.setOnClickListener {
            val username = editRegUser.text.toString()
            val password = editRegPass.text.toString()
            // Validates if fields are empty
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out username and password", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val success = userRepository.registerUser(username, password)
                    runOnUiThread {
                        if (success) {
                            Toast.makeText(this@MainActivity, "Registered! You can login now", Toast.LENGTH_SHORT).show()
                            // Clear fields upon registration
                            editRegUser.text.clear()
                            editRegPass.text.clear()
                        } else {
                            Toast.makeText(this@MainActivity, "Username already exists", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        // Login for existing user, validates credentials against the database
        btnLogin.setOnClickListener {
            val username = editLoginUser.text.toString().trim()
            val password = editLoginPass.text.toString().trim()
            // Checks if the login fields are empty
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter valid username and password", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val isCorrect = userRepository.loginUser(username, password)
                    runOnUiThread {
                        if (isCorrect) {
                            Toast.makeText(this@MainActivity, "Login success!", Toast.LENGTH_SHORT).show()
                            // Allows users to the next screens upon login success
                            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                            intent.putExtra("USERNAME", username)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@MainActivity, "Wrong Credentials", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}