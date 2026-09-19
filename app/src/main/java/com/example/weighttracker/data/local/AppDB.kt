package com.example.weighttracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// This class is now a Room database
@Database(entities = [User::class, WeightEntry::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    // User access to user queries
    abstract fun userDao(): UserDao
    // User access to weight queries
    abstract fun weightDao(): WeightDao

    // With companion object we only use a single db instance for the application
    companion object {
        // All instances are up to date
        @Volatile
        private var INSTANCE: AppDB? = null

        // Retrieves database instance, and creates a db file if it doesn't exist yet
        fun getDatabase(context: Context): AppDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "weight-tracker.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}