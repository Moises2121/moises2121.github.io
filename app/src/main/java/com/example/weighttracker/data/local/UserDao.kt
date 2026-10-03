package com.example.weighttracker.data.local

import androidx.room.*

// Data access for the users table, which handles the login and registration
@Dao
interface UserDao {
    // Searches for the match username, handling null if credentials are wrong
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    // Insert new user to the database, ABORT if username already exists preventing duplicates
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun register(user: User)
}