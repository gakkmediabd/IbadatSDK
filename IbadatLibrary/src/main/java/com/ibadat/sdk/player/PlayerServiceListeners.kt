package com.ibadat.sdk.player

interface PlayerServiceListeners{
    fun bufferingUpdate(percent:Int){}
    fun playbackState(state: STATE){}
    fun durationChange(duration: Int){}
    fun currentPositionChange(position:Int){}

}
