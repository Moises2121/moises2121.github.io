package com.example.weighttracker.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.weighttracker.R

// Notification's Helper object for showing notifications once user reaches the goal
object NotificationHelper {

    private const val CHANNEL_ID = "channel_1"

    // Creates notification channel so It can be displayed on the emulator
    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Goal Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            // Pop-up notification service
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    // Shows message once the goal has been reached, called from dashboard data
    fun showGoalReached(context: Context, goal: Int, current: Int) {
        createChannel(context)
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Goal Reached!")
            .setContentText("You reached $current lbs! Goal was $goal lbs")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        manager.notify(1, notification)
    }
}