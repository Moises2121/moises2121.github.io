package com.example.weighttracker.data.local.repository

import android.content.Context
import com.example.weighttracker.data.local.AppDB
import com.example.weighttracker.data.local.User
import at.favre.lib.crypto.bcrypt.BCrypt

// Class repository handling user operations using Room database
class UserRepository(context: Context) {
    private val dao = AppDB.getDatabase(context).userDao()

    // Returns true if registration is successful, false if user already exists
    suspend fun registerUser(username: String, password: String): Boolean {
        return try {
            // Hash password with BCrypt with auto generated salt, not reversible
            val hash = BCrypt.withDefaults().hashToString(12, password.toCharArray())
            dao.register(User(username, hash))
            true
        } catch (_: Exception) { false }

    }
    // Verifies log in vy comparing plain text pwd with BCrypt hash
    suspend fun loginUser(username: String, password: String): Boolean {
        val user = dao.getUserByUsername(username) ?: return false
        val result = BCrypt.verifyer().verify(password.toCharArray(), user.passwordHash)
        return result.verified
    }
}