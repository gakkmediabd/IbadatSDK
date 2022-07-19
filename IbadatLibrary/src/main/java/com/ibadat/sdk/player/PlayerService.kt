package com.ibadat.sdk.player

import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ibadat.sdk.R
import com.ibadat.sdk.player.data.model.Surah
import com.ibadat.sdk.util.PLAYER_NOTIFICATION_CHANNEL_ID
import com.ibadat.sdk.util.PLAYER_STOP

private const val TAG = "PlayerService"

internal class PlayerService : Service(), PlayerServiceController {
    private val playerBinder: PlayerBinder = PlayerBinder()
    private var mediaPlayer: MediaPlayer? = null
    private var playerServiceListeners: PlayerServiceListeners? = null
    private var isForeground: Boolean = false
    private val localBroadCastReceiver = LocalBroadCastReceiver()
    private lateinit var notificationManager: NotificationManagerCompat
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notificationLayout: RemoteViews
    private var surah: Surah? = null
    private var isReady: Boolean = false

    override fun onCreate() {
        super.onCreate()
        setupMediaPlayer()
        generateNotificationBuilder()
        registerActionReceiver()

        Log.i(TAG, "onCreate: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action != null && intent.action.equals(PLAYER_STOP, ignoreCase = true)) {
            stopForeground(true)
            stopSelf()
        }
        return START_NOT_STICKY
    }

    private fun setupMediaPlayer() {
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }
    }

    override fun prepare(surah: Surah, playWhenReady: Boolean) {
        Log.i(TAG, "prepare: ")
        this.surah = surah

        pause()
        mediaPlayer?.reset()
        mediaPlayer?.setDataSource(this, Uri.parse(surah.playUrl))

        mediaPlayer?.setOnPreparedListener {
            isReady = true
            Log.i(TAG, "duration: $duration")
            playerServiceListeners?.durationChange(duration)
            if (playWhenReady) {
                it.start()
            }
            notifyNotification()
            playerServiceListeners?.playbackState(playBackState)
        }

        mediaPlayer?.setOnBufferingUpdateListener { mp, percent ->
            playerServiceListeners?.bufferingUpdate(percent)
            playerServiceListeners?.playbackState(playBackState)
            Log.i(TAG, "mediaPlayer: $percent ${mp.isPlaying} -${playerServiceListeners == null}")
        }
        mediaPlayer?.prepareAsync()
    }

    override fun play() {
        if (isReady) {
            mediaPlayer?.start()
        }
        playerServiceListeners?.playbackState(playBackState)
        notifyNotification()
    }

    override fun pause() {
        mediaPlayer?.pause()
        playerServiceListeners?.playbackState(playBackState)
        notifyNotification()
    }

    override fun togglePlayPause() {
        if (isPlaying) {
            pause()
        } else {
            play()
        }
    }

    override val isPlaying: Boolean
        get() = mediaPlayer?.isPlaying == true
    override val duration: Int
        get() = mediaPlayer?.duration ?: -1
    override val currentPosition: Int
        get() = mediaPlayer?.currentPosition ?: 0

    override val playBackState: STATE
        get() = if (mediaPlayer?.isPlaying == true) STATE.PLAY else STATE.PAUSE

    override fun seekTo(msec: Int) {
        mediaPlayer?.seekTo(msec)
    }

    override fun release() {
        Log.i(TAG, "release: ")
        stopForeground(true)
        mediaPlayer?.pause()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun generateNotificationBuilder() {
        notificationManager = NotificationManagerCompat.from(this)
        notificationLayout = RemoteViews(packageName, R.layout.player_notification_view)
        notificationLayout.setImageViewResource(
            R.id.play_pause,
            R.drawable.ic_baseline_play_circle_filled_24
        )
        notificationLayout.setImageViewResource(R.id.close, R.drawable.ic_cancel_black_24dp)

        val playPauseIntent = Intent(
            this,
            NotificationBroadcastReceiver::class.java
        ).apply { action = PLAY_PAUSE }
        val close = Intent(
            this,
            NotificationBroadcastReceiver::class.java
        ).apply { action = CLOSE }
        val playPausePendingIntent = PendingIntent.getBroadcast(this, 0, playPauseIntent, 0)
        val closePendingIntent = PendingIntent.getBroadcast(this, 0, close, 0)
        notificationLayout.setOnClickPendingIntent(R.id.play_pause, playPausePendingIntent)
        notificationLayout.setOnClickPendingIntent(R.id.close, closePendingIntent)


        notificationBuilder = NotificationCompat.Builder(this, PLAYER_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.default_img)
            .setCustomContentView(notificationLayout)
    }

    private fun notifyNotification() {
        notificationLayout.setTextViewText(R.id.subtitle, surah?.title ?: "Unknown")
        if (isPlaying) {
            notificationLayout.setImageViewResource(
                R.id.play_pause,
                R.drawable.ic_baseline_pause_circle_filled_24
            )
        } else {
            notificationLayout.setImageViewResource(
                R.id.play_pause,
                R.drawable.ic_baseline_play_circle_filled_24
            )
        }
        if (isForeground) {
            notificationManager.notify(NOTIFY_ID, notificationBuilder.build())
        } else {
            startForeground(NOTIFY_ID, notificationBuilder.build())
            isForeground = true
        }
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG, "onBind: ")
        return playerBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        release()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        unregisterActionReceiver()
        release()
        super.onDestroy()
    }

    private fun registerActionReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(localBroadCastReceiver,
            IntentFilter().apply {
                addAction(CLOSE)
                addAction(PLAY_PAUSE)
            }
        )
    }

    private fun unregisterActionReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(localBroadCastReceiver,
            IntentFilter().apply {
                addAction(CLOSE)
                addAction(PLAY_PAUSE)
            }
        )
    }

    inner class LocalBroadCastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                PLAY_PAUSE -> togglePlayPause()
                CLOSE -> {
                    pause()
                    isForeground = false
                    stopForeground(true)
                }
            }
        }
    }

    inner class PlayerBinder : Binder() {
        val serviceController: PlayerServiceController = this@PlayerService
        fun playerListener(playerServiceListeners: PlayerServiceListeners) {
            this@PlayerService.playerServiceListeners = playerServiceListeners
        }
    }

    companion object {
        const val NOTIFY_ID = 323
        const val PLAY_PAUSE = "com.ibadat.sdk.play_pause"
        const val CLOSE = "com.ibadat.sdk.close"
    }
}