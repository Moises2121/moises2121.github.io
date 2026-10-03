package com.example.weighttracker.data.local.repository

import android.content.Context
import com.example.weighttracker.data.local.AppDB
import com.example.weighttracker.data.local.WeightEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
//Import weightBST as part of enhancement two
import com.example.weighttracker.data.local.structure.WeightBST

//Weight Repository is the single source of truth, the UI and ViewModel cannot access the database directly
class WeightRepository(context: Context) {
    private val dao = AppDB.getDatabase(context).weightDao()


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
            // Allow only one entry per day
            val today = java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.US).format(java.util.Date())
            val existing = dao.getEntryByDate(username, today)
            if (existing != null) {
                // Same day will update it, rather than blocking it
                dao.updateEntry(existing.id, weight, goal)
            } else {
                dao.insert(WeightEntry(username = username, weight = weight, goalWeight = goal, date = today))
            }
            // Always update history goal display
            dao.updateAllGoals(username, goal)
        }
    }
    // set goal without creating 0.0 weight row in history
    suspend fun setGoal(username: String, goal: Double) {
        withContext(Dispatchers.IO) {
            dao.updateAllGoals(username, goal)
        }
    }

    // New method that builds BST  Treemap from history as o(log n)
    private suspend fun getHistoryAsBST(username: String): WeightBST {
        return withContext(Dispatchers.IO) {
            val list = dao.getHistory(username)
            val bst = WeightBST()
            list.forEach { bst.insert(it) }
            bst
        }
    }

    // Wrapper exposing range query to viewmodel
    suspend fun getLastNDaysHistory(username: String, n: Int): List<WeightEntry> {
        return withContext(Dispatchers.IO) {
            val bst = getHistoryAsBST(username)
            bst.getLastNDays(n)
        }
    }

    // Weight change calculation using BST structure
    suspend fun getWeightDifference(username: String): Float {
        return withContext(Dispatchers.IO) {
            getHistoryAsBST(username).getWeightDifference()
        }
    }



}