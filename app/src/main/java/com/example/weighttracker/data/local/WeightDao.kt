package com.example.weighttracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// Object for the weights table. Handling all weight CRUD operators
@Dao
interface WeightDao {
    // Reads current user's weight history ordered newest to oldest
    @Query("SELECT * FROM weights WHERE username = :username ORDER BY id DESC")
    suspend fun getHistory(username: String): List<WeightEntry>

    // Deletes the weight selected by the current user using the weight's ID
    @Query("DELETE FROM weights WHERE id = :id")
    suspend fun deleteWeight(id: Int)

    // Creates (Inserts) new weight into the database
    @Insert
    suspend fun insert(entry: WeightEntry)

    // Gets latest goal weight from current user
    @Query("SELECT goalWeight FROM weights WHERE username = :username ORDER BY id DESC LIMIT 1")
    suspend fun getLastTarget(username: String): Double?
}