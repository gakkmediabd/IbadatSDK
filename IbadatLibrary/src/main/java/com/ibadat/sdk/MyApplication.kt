package com.ibadat.sdk

import android.app.Application
import com.ibadat.sdk.MyApplication
import android.util.DisplayMetrics
import android.os.Build
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Handler
import com.ibadat.sdk.util.PLAYER_NOTIFICATION_CHANNEL_ID
import com.ibadat.sdk.util.PLAYER_NOTIFICATION_CHANNEL_NAME

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
        //CommonAll.changeLocale(this);
        instance = this
        Companion.applicationContext = applicationContext
        applicationHandler = Handler(applicationContext.mainLooper)
        context = applicationContext
        val metrics = this.resources.displayMetrics
        width = metrics.widthPixels
        height = metrics.heightPixels
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                PLAYER_NOTIFICATION_CHANNEL_ID,
                PLAYER_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            channel.description = "This notification use for show ibadat player"
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(channel)
        }
    }

    companion object {
        var width = 0
        var height = 0
        val TAG = MyApplication::class.java
            .simpleName

        @get:Synchronized
        var instance: MyApplication? = null
            private set

        @Volatile
        var applicationContext: Context? = null

        @Volatile
        var applicationHandler: Handler? = null
        var context: Context? = null
            private set
    }
}