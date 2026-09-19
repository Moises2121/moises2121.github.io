package com.example.weighttracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// This class handles both databases (users and weights)
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "fitness.db", null, 2) {

    // Creates the tables on SQLite
    override fun onCreate(db: SQLiteDatabase) {
        // Table 1: for login
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)")
        // Table 2: for weight tracking
        db.execSQL("CREATE TABLE weights (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, weight REAL, target_weight REAL, date TEXT)")
    }

    // Called when we change database version (2) - deletes old tables and creates new
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS weights")
        onCreate(db)
    }

    // --- USER FUNCTIONS --- //

    // New user registration
    fun registerUser(username: String, password: String): Boolean {
        // write data to the table
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("username", username)
        values.put("password", password)

        // insert username to the users table
        val result = db.insert("users", null, values)
        // check for duplicate user from database table
        return result != -1L
    }

    // Check if username and password match from the users database
    fun loginUser(username: String, password: String): Boolean {
        // read data from the table
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", arrayOf(username, password))
        return cursor.count > 0
    }

    // --- WEIGHT FUNCTIONS --- //

    // Save weight entry
    fun addWeight(username: String, weight: Double, target: Double): Boolean {
        // write to the db weight table
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("username", username)
        values.put("weight", weight)
        values.put("target_weight", target)
        values.put("date", System.currentTimeMillis().toString()) // save current time

        val result = db.insert("weights", null, values)
        // check for duplicate user from database table
        return result != -1L
    }

    // Get last goal weight saved for this user
    fun getLastTarget(username: String): Double {
        // read from database
        val db = this.readableDatabase
        // get last goal for this user, newest one first
        val cursor = db.rawQuery("SELECT target_weight FROM weights WHERE username=? ORDER BY id DESC LIMIT 1", arrayOf(username))
        var target = 0.0 // default 0 if there's no goal yet
        if (cursor.moveToFirst()) {
            // get the target weight
            target = cursor.getDouble(0)
        }
        cursor.close()
        // return the goal
        return target
    }

    // --- HISTORY FUNCTIONS --- //

    // data class to hold one weight entry
    data class WeightEntry(val id: Int, val date: String, val weight: Double)

    // get history from user, with new ones on top
    fun getHistory(username: String): List<WeightEntry> {
        val list = mutableListOf<WeightEntry>()
        // read from database
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT id, date, weight FROM weights WHERE username=? ORDER BY id DESC", arrayOf(username))

        // loop through the rows
        while (cursor.moveToNext()) {
            // id
            val id = cursor.getInt(0)
            // date as number
            val millisString = cursor.getString(1)
            // weight
            val weight = cursor.getDouble(2)

            // convert to friendly user date format
            val millis = millisString.toLongOrNull() ?: System.currentTimeMillis()
            val date = java.text.SimpleDateFormat("M/d/yyyy", java.util.Locale.US)
                .format(java.util.Date(millis))

            list.add(WeightEntry(id, date, weight))
        }
        cursor.close()
        return list
    }

    // Delete one weight entry by id
    fun deleteWeight(id: Int): Boolean {
        // write to database
        val db = this.writableDatabase
        // delete by id
        val rowsDeleted = db.delete("weights", "id=?", arrayOf(id.toString()))
        return rowsDeleted > 0
    }
}