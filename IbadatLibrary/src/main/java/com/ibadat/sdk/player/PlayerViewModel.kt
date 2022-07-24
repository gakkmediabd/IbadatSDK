package com.ibadat.sdk.player

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.*
import com.ibadat.sdk.player.data.model.Surah
import kotlinx.coroutines.*

internal class PlayerViewModel : ViewModel() {
    public val playerServiceConnection: PlayerServiceConnection = PlayerServiceConnection()
    public val lifecycleObserver: ActivityLifecycleObserver = ActivityLifecycleObserver()
    private var currentPositionUpdateJob: Job? = null
    private var playerController: PlayerServiceController? = null
    private val _isPlayerReady: MutableLiveData<Boolean> = MutableLiveData(false)
    val isPlayerReady: LiveData<Boolean> = _isPlayerReady

    private val _playbackState: MutableLiveData<STATE> = MutableLiveData(STATE.PAUSE)
    val playbackState: LiveData<STATE> = _playbackState

    private val _playerDuration: MutableLiveData<Int> = MutableLiveData(-1)
    val playerDuration: LiveData<Int> = _playerDuration

    private val _playerCurrentPosition: MutableLiveData<Int> = MutableLiveData(0)
    val playerCurrentPosition: LiveData<Int> = _playerCurrentPosition


    fun togglePlayPause() {
        playerController?.togglePlayPause()
    }

    var shouldNotUpdateCurrentPositionFromPlayer: Boolean = false

    fun seekTo(progress: Any, needSeek: Any): Any {
        Log.i("PlayerService", "viewModel seekTo: $progress $needSeek")
        if (progress is Int && needSeek is Boolean) {
            if (needSeek) {
                playerController?.seekTo(progress)
                shouldNotUpdateCurrentPositionFromPlayer = false
            } else {
                shouldNotUpdateCurrentPositionFromPlayer = true
            }

        }
        return Unit
    }

    fun prepare(surah: Surah, playWhenReady: Boolean) {
        playerController?.prepare(
            surah,
            playWhenReady
        )
    }

    inner class PlayerServiceConnection : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            if (binder is PlayerService.PlayerBinder) {
                playerController = binder.serviceController
                _isPlayerReady.postValue(true)
                Log.i("PlayerService", "onServiceConnected : $name")

                binder.playerListener(object : PlayerServiceListeners {
                    override fun bufferingUpdate(percent: Int) {
                        super.bufferingUpdate(percent)
                        Log.i("PlayerService", "bufferingUpdate : $percent")
                    }

                    override fun playbackState(state: STATE) {
                        super.playbackState(state)
                        _playbackState.postValue(state)
                        currentPositionUpdate()

                        Log.i("PlayerService", "playbackState : $state")
                    }

                    override fun durationChange(duration: Int) {
                        _playerDuration.postValue(duration)
                    }
                })
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            currentPositionUpdateJob?.cancel()
        }
    }

    private fun currentPositionUpdate() {
        Log.i("PlayerService", "startCurrentPositionUpdate")

        viewModelScope.launch {
            while (playerController?.isPlaying == true) {

                if (!shouldNotUpdateCurrentPositionFromPlayer) {
                    Log.i("PlayerService", "current: ${playerController?.currentPosition}")
                    _playerCurrentPosition.postValue(playerController?.currentPosition)
                }
                delay(500)
            }
        }
    }

    inner class ActivityLifecycleObserver : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
        }

        override fun onPause(owner: LifecycleOwner) {
            super.onPause(owner)

        }

        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            playerController?.release()
        }
    }
}