package com.example.weighttracker.data.local.repository

import android.content.Context
import com.example.weighttracker.data.local.AppDB
import com.example.weighttracker.data.local.User

// Class repository handling user operations using Room database
class UserRepository(context: Context) {
    private val dao = AppDB.getDatabase(context).userDao()

    // Returns true if registration is successful, false if user already exists
    suspend fun registerUser(username: String, password: String): Boolean {
        return try {
            dao.register(User(username, password))
            true
        } catch (_: Exception) { false }

    }
    // Returns true if login is successful and valid
    suspend fun loginUser(username: String, password: String): Boolean {
        return dao.login(username, password) != null
    }
}