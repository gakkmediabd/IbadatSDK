package com.ibadat.sdk.player

import com.ibadat.sdk.player.data.model.Surah
import java.text.FieldPosition

interface PlayerServiceController {
    val isPlaying: Boolean?
    val duration: Int
    val currentPosition:Int
    fun prepare(surah: Surah, playWhenReady: Boolean)
    fun play()
    fun pause()
    fun release()
    fun seekTo(msec: Int)


    val playBackState: STATE
    fun togglePlayPause()
}