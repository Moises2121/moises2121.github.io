package com.example.weighttracker.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
//Import weightBST as part of enhancement two
import com.example.weighttracker.data.local.WeightEntry
import com.example.weighttracker.R

// Notification's Helper object for showing notifications once user reaches the goal
object NotificationHelper {

    private const val CHANNEL_ID = "channel_1"

    // Creates notification channel required for Android 8
    fun createChannel(context: Context) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Goal Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

    // // Now it takes BST instead of list for last entry check
    fun showGoalReachedIfNeeded(context: Context, lastEntry: WeightEntry, goal: Int) {
        // gets current weight directly from BST
        val currentWeight = lastEntry.weight.toInt()
        // calculates progress using current and goal
        val progress = (currentWeight.toDouble() / goal * 100).toInt()
        //builds notification from WeightEntry
        if (progress >= 100) {
            showGoalReached(context, goal, currentWeight)
        }
    }

    // Shows notification when goal is reached
    fun showGoalReached(context: Context, goal: Int, current: Int) {
        createChannel(context)
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(context.getString(R.string.goal_reached_title))
            .setContentText(context.getString(R.string.goal_reached_body, current, goal))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .build()

        manager.notify(1, notification)
    }
}