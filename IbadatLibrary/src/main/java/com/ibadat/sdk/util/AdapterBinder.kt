package com.ibadat.sdk.util

import android.widget.ImageView
import android.widget.SeekBar
import androidx.databinding.BindingAdapter
import com.ibadat.sdk.R
import com.ibadat.sdk.player.STATE

@BindingAdapter("playbackState")
fun ImageView.playbackState(state: STATE?) {
    when (state) {
        STATE.PLAY -> setImageResource(R.drawable.ic_mini_player_pause)
        STATE.PAUSE -> setImageResource(R.drawable.ic_mini_player_play)
        else -> setImageResource(R.drawable.ic_mini_player_play)
    }

}

@BindingAdapter("playerDuration")
fun SeekBar.playerDuration(playerDuration: Int?) {
    if (playerDuration != null && playerDuration != -1) {
        this.max = playerDuration
    }
}

@BindingAdapter("playerCurrentPosition")
public fun SeekBar.playerCurrentPosition(position: Int?) {
    if (position != null && position != -1) {
        this.progress = position
    }
}

@BindingAdapter("playerAction")
public fun SeekBar.playerAction(playerAction: ((progress: Int, needSeek: Boolean) -> Unit)?) {

    this.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            if (fromUser) {
                playerAction?.invoke(progress, false)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            playerAction?.invoke(progress, true)
        }
    })
}