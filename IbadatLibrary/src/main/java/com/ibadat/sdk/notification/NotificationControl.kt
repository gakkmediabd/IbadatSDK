package com.ibadat.sdk.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ibadat.sdk.R
import com.ibadat.sdk.broadcust_recever.AlarmRequestBReceiver
import com.ibadat.sdk.roza.NotificationDismissedBReceiver
import com.ibadat.sdk.util.*


internal object NotificationControl {
    private lateinit var notificationManager: NotificationManager

    fun initNotificationManager(context: Context) {
        if (!this::notificationManager.isInitialized) {
            notificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        }
    }

    fun getNotificationManager() = notificationManager

    fun createNotificationChannel(channelName: String, description: String, channelID: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(NotificationChannel(
                channelID,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                this.description = description
                setSound(null, null)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            })
        }
    }

    fun showNotification(
        context: Context,
        notificationId: Int,
        builder: NotificationCompat.Builder
    ) {
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }

    @SuppressLint("RemoteViewLayout", "UnspecifiedImmutableFlag")
    fun generateCustomNotification(context: Context, packageName: String): RemoteViews {
//        val notificationManagerCompat = NotificationManagerCompat.from(context)
        val notificationLayout = RemoteViews(packageName, R.layout.alarm_notification_view)

        notificationLayout.setTextViewText(
            R.id.tv_notification_title,
            context.resources.getString(R.string.text_alarm)
        )
        notificationLayout.setTextViewText(
            R.id.tv_notification_message,
            context.resources.getString(R.string.app_name)
        )
        notificationLayout.setImageViewResource(
            R.id.iv_notification_dismiss,
            R.drawable.ic_cancel_black_24dp
        )

        val close = Intent(
            context,
            AlarmRequestBReceiver::class.java
        ).apply {
            putExtra(
                ACTION_PASS_ALARM_SERVICE_TYPE,
                DISMISS_CURRENT_NOTIFICATION_SERVICE
            )
        }
        notificationLayout.setOnClickPendingIntent(
            R.id.iv_notification_dismiss,
            PendingIntent.getBroadcast(context, 0, close, PendingIntent.FLAG_CANCEL_CURRENT)
        )
        return notificationLayout
    }

    @SuppressLint("InlinedApi")
    fun createNotification(
        context: Context,
        channelID: String,
        message: String,
        isVibrating: Boolean
    ): NotificationCompat.Builder {
        val pmAppManager: PackageManager = context.packageManager
        val packageName = context.packageName
        val intent: Intent = pmAppManager.getLaunchIntentForPackage(packageName)!!

        return NotificationCompat.Builder(context, channelID)
            .apply {
                setSmallIcon(R.drawable.ic_notifications)
                setCustomContentView(generateCustomNotification(context, packageName))
                setContentTitle(context.resources.getString(R.string.app_name))
                setContentText(message)
                setOngoing(false)
                setDeleteIntent(notificationDeleteIntent(context))
                priority = NotificationCompat.PRIORITY_HIGH
                setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                setContentIntent(
                    PendingIntent.getActivity(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                )
                setAutoCancel(true)
                if (isVibrating) {
                    setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                }
            }
    }

    fun createOnDismissedIntent(context: Context): PendingIntent {
        val intent = Intent(
            context,
            NotificationDismissedBReceiver::class.java
        )
        intent.putExtra(
            ACTION_PASS_ALARM_SERVICE_TYPE,
            DISMISS_IFTER_ALARM_SERVICE
        )

        return PendingIntent.getBroadcast(
            context.applicationContext,
            ROZA_NOTIFICATION_ID,
            intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun notificationDeleteIntent(context: Context): PendingIntent {
        Log.e("", "getNotificationDeleteIntent: " + "delete")
        return PendingIntent.getBroadcast(
            context,
            DISMISS_NOTIFICATION_SERVICE_CODE,
            Intent(context, AlarmRequestBReceiver::class.java)
                .putExtra(ACTION_PASS_ALARM_SERVICE_TYPE, DISMISS_CURRENT_NOTIFICATION_SERVICE),
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )
    }
}