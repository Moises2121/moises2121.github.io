package com.example.weighttracker.data.local.repository

import android.content.Context
import com.example.weighttracker.data.local.AppDB
import com.example.weighttracker.data.local.WeightEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//Weight Repository is the single source of truth, the UI and ViewModel cannot access the database directly
class WeightRepository(context: Context) {
    private val dao = AppDB.getDatabase(context).weightDao()

    // Gets full history of current user
    suspend fun getHistory(username: String): List<WeightEntry> {
        return withContext(Dispatchers.IO) {
            dao.getHistory(username)
        }
    }

    // Gets latest saved goal
    suspend fun getLastTarget(username: String): Double? {
        return withContext(Dispatchers.IO) {
            dao.getLastTarget(username)
        }
    }

    // Deletes single weight entry by ID
    suspend fun deleteWeight(id: Int) {
        withContext(Dispatchers.IO) {
            dao.deleteWeight(id)
        }
    }

    // Creates new weight entry for logged-in user
    suspend fun addWeight(username: String, weight: Double, goal: Double) {
        withContext(Dispatchers.IO) {
            dao.insert(WeightEntry(username = username, weight = weight, goalWeight = goal))
        }
    }
}