package com.noxis.audiorecorder

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class AudioRecordApp: Application() {

    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            "running_channel",
            "Running notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}