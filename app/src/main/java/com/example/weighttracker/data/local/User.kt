package com.example.weighttracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// User table that handles the "Welcome, user" feature I added
@Entity(tableName = "users")
data class User(
    // Unique username set as primary key and for the welcome user feature
    @PrimaryKey val username: String,
    // Password storage for version 2 of the app
    val password: String
)