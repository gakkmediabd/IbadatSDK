package com.ibadat.sdk.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.util.DisplayMetrics

object GlobalVar {
    const val CURRENT_WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val CURRENCY_BASE_URL = "http://data.fixer.io/api/"
    const val QURAN_BASE_URL = "http://118.67.219.130:801/api/"
    const val DUA_BASE_URL = "http://43.240.103.34/ebadattest/api/"
    const val SUBS_BASE_URL = "http://ibadat.co/dpdpapi/"
    var CITY_NAME = "Searching.."
    var mMediaPlayer: MediaPlayer? = null
    const val BASE_URL_NEARBY_PLACE = "https://maps.googleapis.com/maps/api/place/nearbysearch/"
    const val RAMADAN_TIMING_BASE_URL = "http://27.131.15.12:801/api/"

    @Volatile
    var height = 5000

    @Volatile
    var width = 400
    val mediaPlayer: MediaPlayer?
        get() {
            mMediaPlayer = MediaPlayer()
            return mMediaPlayer
        }

    fun screenSize(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels
        width = displayMetrics.widthPixels
        return width
    }

    var ROOT_URL = "http://43.240.103.34/ebadattest/api/"
    const val BASE_URL_SUBSCRIPTION = "http://api.gakkplay.com/ibadat/core/"
    const val BASE_URL_DOWNLOAD_CHECK = "http://43.240.103.34/ebadattest/"
    const val ISLAMICHOLIDAYs_URL = "http://118.67.219.130:801/api/"
    val AyetCount = arrayOf(
        "7",
        "286",
        "200",
        "176",
        "120",
        "165",
        "206",
        "75",
        "129",
        "109",
        "123",
        "111",
        "43",
        "52",
        "99",
        "128",
        "111",
        "110",
        "98",
        "135",
        "112",
        "78",
        "118",
        "64",
        "77",
        "227",
        "13",
        "88",
        "69",
        "60",
        "34",
        "30",
        "73",
        "54",
        "45",
        "83",
        "182",
        "88",
        "75",
        "85",
        "54",
        "53",
        "89",
        "59",
        "37",
        "35",
        "38",
        "29",
        "18",
        "45",
        "60",
        "49",
        "62",
        "55",
        "78",
        "96",
        "21",
        "22",
        "24",
        "13",
        "14",
        "11",
        "11",
        "18",
        "12",
        "12",
        "30",
        "52",
        "52",
        "44",
        "28",
        "28",
        "20",
        "56",
        "40",
        "31",
        "50",
        "40",
        "46",
        "42",
        "29",
        "19",
        "36",
        "25",
        "22",
        "17",
        "19",
        "26",
        "30",
        "20",
        "15",
        "21",
        "11",
        "8",
        "8",
        "19",
        "5",
        "8",
        "8",
        "11",
        "11",
        "8",
        "3",
        "9",
        "5",
        "4",
        "7",
        "3",
        "6",
        "3",
        "5",
        "4",
        "5",
        "6"
    )
    var permissions = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.VIBRATE,
        Manifest.permission.RECORD_AUDIO
    )
}