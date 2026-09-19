package com.example.weighttracker.data.local.repository

import android.content.Context
import com.example.weighttracker.data.local.AppDB
import com.example.weighttracker.data.local.User

class UserRepository(context: Context) {
    private val dao = AppDB.getDatabase(context).userDao()

    suspend fun registerUser(username: String, password: String): Boolean {
        return try {
            dao.register(User(username, password))
            true
        } catch (e: Exception) { false }
    }
    suspend fun loginUser(username: String, password: String): Boolean {
        return dao.login(username, password) != null
    }
}