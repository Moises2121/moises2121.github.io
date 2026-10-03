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

    // Updates goal for all entries without inserting a default 0.0 entry
    @Query("UPDATE weights SET goalWeight = :newGoal WHERE username = :username")
    suspend fun updateAllGoals(username: String, newGoal: Double)

    // Uses username FK and date to enforce one entry per day
    @Query("SELECT * FROM weights WHERE username = :username AND date = :date LIMIT 1")
    suspend fun getEntryByDate(username: String, date: String): WeightEntry?

    // Overwrites entry if another entry occurs the same day
    @Query("UPDATE weights SET weight = :weight, goalWeight = :goal WHERE id = :id")
    suspend fun updateEntry(id: Int, weight: Double, goal: Double)
}