package com.example.weighttracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// Each row from the weight's table, each belonging to the user via username
@Entity(tableName = "weights")
data class WeightEntry(
    // Generates unique ID for each weight entry, used to identify records to delete
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    // Current user
    val username: String,
    // Weight entered by user
    val weight: Double,
    // Goal weight that serves to track progress and display history
    val goalWeight: Double,
    // Generate the date for the entry, used to be displayed in history
    val date: String = java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.US).format(java.util.Date())
)