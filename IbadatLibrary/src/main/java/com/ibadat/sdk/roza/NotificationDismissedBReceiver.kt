package com.ibadat.sdk.roza

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


internal class NotificationDismissedBReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        AzanPlayer.releaseMediaPlayer()
    }
}