package com.ibadat.sdk.broadcust_recever.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.ibadat.sdk.notification.NotificationControl
import com.ibadat.sdk.roza.AzanPlayer
import com.ibadat.sdk.util.*
import com.ibadat.sdk.util.AppConstantUtils
import com.ibadat.sdk.util.Util

internal class AlarmService : Service() {
    var message: String = ""
    var isForeGroundService = false
    override fun onCreate() {
        super.onCreate()
        isForeGroundService = false
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
//        val intentData = intent.getStringExtra(START_FAJR_ALARM_SERVICE)
//        Log.e("AS", "onStartCommand: $intentData")
        NotificationControl.initNotificationManager(this)
        NotificationControl.createNotificationChannel(
            NOTIFICATION_CHANNEL_NAME,
            NOTIFICATION_CHANNEL_DESC, NOTIFICATION_CHANNEL_ID
        )
        AzanPlayer.playAzanFromRawFolder(
            this,
            Util.getUriFromPath(this, AppConstantUtils.raw + "azan_fajr.mp3")
        )
        val builder = NotificationControl.createNotification(
            this, NOTIFICATION_CHANNEL_ID,
            message,
            true
        )
        val notification = builder.build()
        startForeground(NOTIFICATION_ID, notification)

        return START_STICKY
    }

    override fun onDestroy() {
        AzanPlayer.releaseMediaPlayer()
        super.onDestroy()
    }

    private fun stopService() {
        try {
            stopForeground(true)
            isForeGroundService = false
            stopSelf()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}