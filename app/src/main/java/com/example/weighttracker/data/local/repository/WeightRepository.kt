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
            dao.insert(WeightEntry(username = username, weight = weight, goalWeight = goal))
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

    // Get recent weight entry via BST
    suspend fun getLastEntry(username: String): WeightEntry? {
        return withContext(Dispatchers.IO) {
            val bst = getHistoryAsBST(username)
            bst.getLastEntry()
        }
    }

    // Weight change calculation using BST structure
    suspend fun getWeightDifference(username: String): Float {
        return withContext(Dispatchers.IO) {
            getHistoryAsBST(username).getWeightDifference()
        }
    }



}