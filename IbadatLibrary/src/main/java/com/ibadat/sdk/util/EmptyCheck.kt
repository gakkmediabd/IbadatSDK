package com.ibadat.sdk.util

import android.text.TextUtils

/**
 * Created by user on 10/10/2017.
 */
object EmptyCheck {
    fun isEmpty5length(str: CharSequence?): Boolean {
        return str == null || str.length < 5
    }

    fun isEmpty(str: CharSequence?): Boolean {
        return str == null || str.length == 0
    }

    fun isEmptyMSISDN(mMobileNumberView: CharSequence?): Boolean {
        return !TextUtils.isEmpty(mMobileNumberView)
    }
}