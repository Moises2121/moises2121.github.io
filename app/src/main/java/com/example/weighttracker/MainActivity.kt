package com.example.weighttracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DatabaseHelper(this)

        // first find all views from activity main XML
        val editLoginUser = findViewById<EditText>(R.id.editLoginUser)
        val editLoginPass = findViewById<EditText>(R.id.editLoginPass)
        val editRegUser = findViewById<EditText>(R.id.editRegUser)
        val editRegPass = findViewById<EditText>(R.id.editRegPass)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // --- REGISTER BUTTON ---
        btnRegister.setOnClickListener {
            val username = editRegUser.text.toString()
            val password = editRegPass.text.toString()

            // Check if any field is empty to display an error
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out username and password", Toast.LENGTH_SHORT).show()
            } else {
                // Try to register into the database
                val success = db.registerUser(username, password)
                if (success) {
                    Toast.makeText(this, "Registered! You can login now", Toast.LENGTH_SHORT).show()
                    // reset username and password fields
                    editRegUser.text.clear()
                    editRegPass.text.clear()
                } else {
                    Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // --- LOGIN BUTTON --- //
        btnLogin.setOnClickListener {
            val username = editLoginUser.text.toString().trim()
            val password = editLoginPass.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter valid username and password", Toast.LENGTH_SHORT).show()
            } else {
                // Check if the user exists in the database
                val isCorrect = db.loginUser(username, password)
                if (isCorrect) {
                    Toast.makeText(this, "Login success!", Toast.LENGTH_SHORT).show()

                    // Go to Dashboard screen after successful login
                    val intent = Intent(this, DashboardActivity::class.java)
                    // pass user to next screen
                    intent.putExtra("USERNAME", username)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Wrong Credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}