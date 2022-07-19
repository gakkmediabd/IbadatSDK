package com.ibadat.sdk.util

import android.util.Log

internal class ClickEventCounter {


    companion object {
        @JvmStatic
        var COUNT = 0
        var willShow = false
        fun setCountClickEvent() {
            COUNT++
            willShow = if (COUNT >= 5) {
                true
            } else {
                false
            }
            Log.d("willshow", "countCount $COUNT ")
        }
    }


    fun getStatus(): Boolean {
        return willShow
    }

}
