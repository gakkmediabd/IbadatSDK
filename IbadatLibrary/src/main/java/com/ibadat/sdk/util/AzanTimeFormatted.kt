package com.ibadat.sdk.util

import com.ibadat.sdk.azan.*
import com.ibadat.sdk.roza.TimeFormtter

class AzanTimeFormatted(private val azanTimes: AzanTimes) {
    fun getFajr(): Time {
        return azanTimes.fajr()
    }

    fun getDuhr(): String {
        return TimeFormtter.get12HrFormattedStringFrom24HrFormattedString(
            azanTimes.thuhr().toString()
        )
    }

    fun getAsr(): String {
        return TimeFormtter.get12HrFormattedStringFrom24HrFormattedString(
            azanTimes.assr().toString()
        )
    }

    fun getMagrib(): String {
        return TimeFormtter.get12HrFormattedStringFrom24HrFormattedString(
            azanTimes.maghrib().toString()
        )
    }

    fun getIsha(): String {
        return TimeFormtter.get12HrFormattedStringFrom24HrFormattedString(
            azanTimes.ishaa().toString()
        )
    }
}