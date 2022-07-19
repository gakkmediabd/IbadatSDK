package com.ibadat.sdk.broadcust_recever

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.ibadat.sdk.broadcust_recever.service.AlarmService
import com.ibadat.sdk.roza.AzanPlayer
import com.ibadat.sdk.util.*
import kotlin.math.abs

internal class AlarmRequestBReceiver : BroadcastReceiver() {
    private var alarmMgr: AlarmManager? = null
    private var pAlarmIntent: PendingIntent? = null

    //    private lateinit var mContext: Context
    private var FIVE_MINUTES: Long = 60000 * 5
//    private var Request_ID = 1010

    override fun onReceive(context: Context, intent: Intent) {
//        this.mContext = context
        val receivedIntentValue = intent.getStringExtra(ACTION_PASS_ALARM_SERVICE_TYPE)
        Log.e("ARBR", "onReceive: " + intent.extras)
        val prayerTime = intent.getLongExtra(ACTION_PASS_ALARM_TIME, -1)
        val timePassed =
            prayerTime != -1L && abs(System.currentTimeMillis() - prayerTime) > FIVE_MINUTES
        if (!timePassed) {
            when (receivedIntentValue) {
                START_SEHRI_ALARM_SERVICE -> {
                    val intentSer = Intent(context, AlarmService::class.java)
//                    intent.action = "START_SEHRI_ALARM_SERVICE"
                    startService(context, intentSer)
                }
                START_IFTER_ALARM_SERVICE -> {
                    val intentSer = Intent(context, AlarmService::class.java)
                    startService(context, intentSer)
                }
                START_FAJR_ALARM_SERVICE -> {
                    val intentSer = Intent(context, AlarmService::class.java)
//                    intentSer.putExtra(START_FAJR_ALARM_SERVICE, "dsjdhsjdjk")
                    startService(context, intentSer)
                }
                START_DHUHR_ALARM_SERVICE -> {
                    Intent(context, AlarmService::class.java)
                        .also {
                            startService(context, it)
                        }
                }
                START_ASR_ALARM_SERVICE -> {
                    Intent(context, AlarmService::class.java)
                        .also {
                            startService(context, it)
                        }
                }
                START_MAGRIB_ALARM_SERVICE -> {
                    Intent(context, AlarmService::class.java)
                        .also {
                            startService(context, it)
                        }
                }
                START_ISHA_ALARM_SERVICE -> {
                    Intent(context, AlarmService::class.java)
                        .also {
                            startService(context, it)
                        }
                }
                START_SUNRISE_ALARM_SERVICE -> {
                    Intent(context, AlarmService::class.java)
                        .also {
                            startService(context, it)
                        }
                }
                DISMISS_CURRENT_NOTIFICATION_SERVICE -> {
                    AzanPlayer.releaseMediaPlayer()
                    Intent(context, AlarmService::class.java)
                        .also {
                            context.stopService(it)
                        }
                }
            }
        }
    }

    private fun startService(context: Context, it: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(it)
            context.startService(it)
        } else {
            context.startService(it)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun setAlarm(context: Context, alarmTime: Long, requestType: String, mRequestCode: Long) {
        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmRequestBReceiver::class.java)
        Log.e("ARBR", "setAlarm: $alarmTime")
        intent.putExtra(ACTION_PASS_ALARM_SERVICE_TYPE, requestType)
        intent.putExtra(ACTION_PASS_ALARM_TIME, alarmTime)
        pAlarmIntent =
            PendingIntent.getBroadcast(
                context,
                mRequestCode.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        alarmMgr!!.setAlarmClock(
            AlarmManager.AlarmClockInfo(alarmTime, pAlarmIntent),
            pAlarmIntent
        )
//        alarmMgr!!.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, alarmTime, pAlarmIntent);
    }

    fun cancelAlarm(context: Context?, requestType: String, mRequestCode: Long) {
        context?.let {
            if (alarmMgr == null) {
                alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            }
        }
        alarmMgr?.let {
            if (pAlarmIntent == null) {
                val intent = Intent(context, AlarmRequestBReceiver::class.java)
                intent.putExtra(ACTION_PASS_ALARM_SERVICE_TYPE, requestType)
                pAlarmIntent = PendingIntent.getBroadcast(
                    context,
                    mRequestCode.toInt(),
                    intent,
                    PendingIntent.FLAG_CANCEL_CURRENT
                )
            }
            it.cancel(pAlarmIntent)
        }
    }
}