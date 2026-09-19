package com.example.weighttracker.data.local

import androidx.room.*

// Data access for the users table, which handles the login and registration
@Dao
interface UserDao {
    // Searches for the match username and password, handling null if credentials are wrong
    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): User?

    // Insert new user to the database, ABORT if username already exists preventing duplicates
    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun register(user: User)
}