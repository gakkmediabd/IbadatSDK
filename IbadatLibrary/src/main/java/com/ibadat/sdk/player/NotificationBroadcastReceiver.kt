package com.ibadat.sdk.player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

internal class NotificationBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent !=null) {
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
        }
    }

}