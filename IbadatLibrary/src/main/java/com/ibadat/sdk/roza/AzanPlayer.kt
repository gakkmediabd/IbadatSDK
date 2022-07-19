package com.ibadat.sdk.roza

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri


internal object AzanPlayer {
    private var mMediaPlayer: MediaPlayer? = null
    fun playAzanFromRawFolder(context: Context, uri: Uri) {
        releaseMediaPlayer()
        mMediaPlayer = MediaPlayer().apply {
            setDataSource(context, uri)
        }

        mMediaPlayer?.setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        )
        mMediaPlayer?.prepare()
        mMediaPlayer?.setOnPreparedListener {
            it?.start()
        }
    }

    fun releaseMediaPlayer() {
        mMediaPlayer?.stop()
        mMediaPlayer?.release()
        mMediaPlayer = null
    }
}