package com.ibadat.sdk.data.manager.prefs

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibadat.sdk.data.model.Data
import com.ibadat.sdk.data.model.UserLocation
import com.ibadat.sdk.data.model.roza.IfterAndSehriTime
import com.ibadat.sdk.util.DEFAULT_0_TIME
import com.ibadat.sdk.util.DefaultLocationProvider
import com.ibadat.sdk.util.LAN_BANGLA
import com.ibadat.sdk.util.USER_CURRENT_LOCATION
import java.lang.reflect.Type

object AppPreference {
    private const val LANGUAGE = "Language"
    private const val PREF_FILE_NAME = "IbadatSDKPreference"
    private const val TOTALCOUNTTAG = "totalcount"
    private const val ISNOTIFICATIONON = "isNotificationOn"
    private const val ISLOCATIONON = "isLocationOn"
    private const val ISSOUNDON = "isSoundOn"
    private const val PREF_USER = "user"
    private const val PREF_RAMADAN_SEHRI_IFTER_LIST = "ramadanSehriIfterList"
    private const val PREF_NEXT_TEN_DAYS_SEHRI_IFTER_LIST = "nextTenDaysSehriIfterList"
    private const val PREF_RAMADAN_NOTIFICATION_SEHRI_ALERT_ON = "ramadanNotificationSehriAlertOn"
    private const val PREF_RAMADAN_NOTIFICATION_IFTER_ALERT_ON = "ramadanNotificationIfterAlertOn"
    private const val PREF_RAMADAN_NOTIFICATION_SOUND_ON = "ramadanNotificationSoundOn"
    private const val PREF_RAMADAN_NOTIFICATION_VIBRATION_ON = "ramadanNotificationVibrationOn"
    private const val PREF_LAST_SET_SEHRI_ALARM_TIME = "lastSavedSehriAlarmTime"
    private const val PREF_LAST_SET_IFTER_ALARM_TIME = "lastSavedIfterAlarmTime"
    private const val PREF_SET_SEHRI_ALARM_TIME = "currentDateSehriAlarmTime"

    private const val PREF_SDK_DOWNLOAD_RESOURCE_STATUS = "sdkDownloadResourceStatus"
    private const val PREF_SDK_EXTRACT_RESOURCE_STATUS = "sdkExtractResourceStatus"

    //This const Pref used for set Daily azan
    private const val IS_PREF_SET_FAJR_ALARM = "currentDateSehriAlarmTime"
    private const val IS_PREF_SET_SUNRISE_ALARM = "currentDateSehriAlarmTime"
    private const val IS_PREF_SET_DHUHR_ALARM = "currentDateSehriAlarmTime"
    private const val IS_PREF_SET_ASR_ALARM = "currentDateSehriAlarmTime"
    private const val IS_PREF_SET_MAGHRIB_ALARM = "currentDateSehriAlarmTime"
    private const val IS_PREF_SET_ISHA_ALARM = "currentDateSehriAlarmTime"

    private const val PREF_SET_IFTER_ALARM_TIME = "currentDateIfterAlarmTime"

    private const val PREF_LAST_LATITUDE = "lastLatitude"
    private const val PREF_LAST_LONGITUDE = "lastLongitude"
    private const val PREF_LAST_LOCATION_NAME = "lastLocationName"

    private lateinit var preferences: SharedPreferences

    private lateinit var mGSonInstance: Gson
    private lateinit var SET_LANGUAGE: Pair<String, String>
    private const val MODE = Context.MODE_PRIVATE
    private val SOUND_FLAG = Pair(ISSOUNDON, true)
    private val TOTAL_COUNT = Pair(TOTALCOUNTTAG, 0)
    private val NOTIFICATION_FLAG = Pair(ISNOTIFICATIONON, true)

    fun initSharedPreferences(context: Context) {
        preferences = context.getSharedPreferences(PREF_FILE_NAME, MODE)
        mGSonInstance = Gson()
        SET_LANGUAGE = Pair(LANGUAGE, LAN_BANGLA)
    }

    fun storeAnyDataPreferences(dataTypeName: String, customerObject: Any?) {
        val savePreferences: SharedPreferences.Editor = preferences.edit()
        val gson = Gson()
        val json = gson.toJson(customerObject)
        savePreferences.putString(dataTypeName, json)
        savePreferences.apply()
    }

    fun getAnyObjectFromSharedPreferences(dataName: String, customerObject: Any): Any? {
        val gson = Gson()
        val json: String = preferences.getString(dataName, "")!!
        return gson.fromJson(json, customerObject.javaClass)
    }

    fun getAnyObjectFromSharedPreferences(dataName: String, type: Type): Any? {
        val gson = Gson()
        val json: String = preferences.getString(dataName, "")!!
        return gson.fromJson(json, type)
    }

    inline fun <reified T> genericType() = object : TypeToken<T>() {}.type

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var cachedUser: Data?
        set(value) {
            val userDataStr = mGSonInstance.toJson(value)
            preferences.edit { it.putString(PREF_USER, userDataStr) }
        }
        get() {
            val userDataStr = preferences.getString(PREF_USER, "")
            val type: Type = genericType<Data>()
            return mGSonInstance.fromJson(userDataStr, type)
        }
    var _ramadanSehriIfterTimes: MutableList<IfterAndSehriTime>? = mutableListOf()

    var ramadanSehriIfterTimes: MutableList<IfterAndSehriTime>?
        set(value) {
            _ramadanSehriIfterTimes = value
        }
        get() {
            return _ramadanSehriIfterTimes
        }

    var _nextTenDaysSehriIfterTimes: MutableList<IfterAndSehriTime>? = mutableListOf()
    var nextTenDaysSehriIfterTimes: MutableList<IfterAndSehriTime>?
        set(value) {
            _nextTenDaysSehriIfterTimes = value
        }
        get() {
            return _nextTenDaysSehriIfterTimes
        }

    fun saveUserCurrentLocation(location: UserLocation) {
        val userCurLocString = mGSonInstance.toJson(location)
        preferences.edit { it.putString(USER_CURRENT_LOCATION, userCurLocString) }
    }

    fun getUserCurrentLocation(): UserLocation {
        val userCurLocString = preferences.getString(USER_CURRENT_LOCATION, "")
        val countryName: String
        countryName = "BANGLADESH"
        if (userCurLocString == null || userCurLocString.length < 1) {
            val mLocation = UserLocation(
                DefaultLocationProvider.getDefaultLat(countryName),
                DefaultLocationProvider.getDefaultLang(countryName)
            )
            return mLocation
        }
        val type: Type = genericType<UserLocation>()
        Log.e("AP", "getUserCurrentLocation: $userCurLocString")
        return mGSonInstance.fromJson(userCurLocString, type)
    }

    var isSahriAlarmOnOff: Boolean
        get() = preferences.getBoolean(PREF_LAST_SET_SEHRI_ALARM_TIME, false)
        set(value) = preferences.edit() {
            it.putBoolean(PREF_LAST_SET_SEHRI_ALARM_TIME, value)
        }

    var isSDkResourceDownloadStatus: Boolean
        get() = preferences.getBoolean(PREF_SDK_DOWNLOAD_RESOURCE_STATUS, false)
        set(value) = preferences.edit() {
            it.putBoolean(PREF_SDK_DOWNLOAD_RESOURCE_STATUS, value)
        }

    var isSDkResourceExtractStatus: Boolean
        get() = preferences.getBoolean(PREF_SDK_EXTRACT_RESOURCE_STATUS, false)
        set(value) = preferences.edit() {
            it.putBoolean(PREF_SDK_EXTRACT_RESOURCE_STATUS, value)
        }

    var isIfterAlarmOnOff: Boolean
        get() = preferences.getBoolean(PREF_LAST_SET_IFTER_ALARM_TIME, false)
        set(value) = preferences.edit() {
            it.putBoolean(PREF_LAST_SET_IFTER_ALARM_TIME, value)
        }

    var setSahriAlarmTime: String
        get() = preferences.getString(PREF_SET_SEHRI_ALARM_TIME, DEFAULT_0_TIME).toString()
        set(value) = preferences.edit() {
            it.putString(PREF_SET_SEHRI_ALARM_TIME, value)
        }

    var setIfterAlarmTime: String
        get() = preferences.getString(PREF_SET_IFTER_ALARM_TIME, DEFAULT_0_TIME).toString()
        set(value) = preferences.edit() {
            it.putString(PREF_SET_IFTER_ALARM_TIME, value)
        }

    //set daily azan Pref
    var isFajrAlarmOnOff: Boolean
        get() = preferences.getBoolean(IS_PREF_SET_FAJR_ALARM, false)
        set(value) = preferences.edit() {
            it.putBoolean(IS_PREF_SET_FAJR_ALARM, value)
        }

    var isSunriseAlarmOnOff: Boolean
        get() = preferences.getBoolean(IS_PREF_SET_SUNRISE_ALARM, false)
        set(value) = preferences.edit() {
            it.putBoolean(IS_PREF_SET_SUNRISE_ALARM, value)
        }

    var isDhuhrAlarmOnOff: Boolean
        get() = preferences.getBoolean(IS_PREF_SET_DHUHR_ALARM, false)
        set(value) = preferences.edit() {
            it.putBoolean(IS_PREF_SET_DHUHR_ALARM, value)
        }

    var isAsrAlarmOnOff: Boolean
        get() = preferences.getBoolean(IS_PREF_SET_ASR_ALARM, false)
        set(value) = preferences.edit() {
            it.putBoolean(IS_PREF_SET_ASR_ALARM, value)
        }

    var isMaghribAlarmOnOff: Boolean
        get() = preferences.getBoolean(IS_PREF_SET_MAGHRIB_ALARM, false)
        set(value) = preferences.edit() {
            it.putBoolean(IS_PREF_SET_MAGHRIB_ALARM, value)
        }

    var isIshaAlarmOnOff: Boolean
        get() = preferences.getBoolean(IS_PREF_SET_ISHA_ALARM, false)
        set(value) = preferences.edit() {
            it.putBoolean(IS_PREF_SET_ISHA_ALARM, value)
        }

    var sehriAlertOn: Boolean
        get() = preferences.getBoolean(PREF_RAMADAN_NOTIFICATION_SEHRI_ALERT_ON, false)
        set(value) = preferences.edit {
            it.putBoolean(PREF_RAMADAN_NOTIFICATION_SEHRI_ALERT_ON, value)
        }

    var lastRozaAlarmDisMissTimeMs: Long
        get() = preferences.getLong("last_roza_alrm_dismiss_time_ms", 0L)
        set(value) = preferences.edit {
            it.putLong("last_roza_alrm_dismiss_time_ms", value)
            Log.e("RECEIVER", "$value")
        }

    var lastLatitude: String
        get() = preferences.getString(PREF_LAST_LATITUDE, "0.0")!!
        set(value) = preferences.edit {
            it.putString(PREF_LAST_LATITUDE, value)
        }

    var lastLongitude: String
        get() = preferences.getString(PREF_LAST_LONGITUDE, "0.0")!!
        set(value) = preferences.edit {
            it.putString(PREF_LAST_LONGITUDE, value)
        }

    var lastLocationName: String
        get() = preferences.getString(PREF_LAST_LOCATION_NAME, "Dhaka, Bangladesh")!!
        set(value) = preferences.edit {
            it.putString(PREF_LAST_LOCATION_NAME, value)
        }

    var ifterAlertOn: Boolean
        get() = preferences.getBoolean(PREF_RAMADAN_NOTIFICATION_IFTER_ALERT_ON, false)
        set(value) = preferences.edit {
            it.putBoolean(PREF_RAMADAN_NOTIFICATION_IFTER_ALERT_ON, value)
        }

    var sehriOrifterAlertSoundOn: Boolean
        get() = preferences.getBoolean(PREF_RAMADAN_NOTIFICATION_SOUND_ON, false)
        set(value) = preferences.edit {
            it.putBoolean(PREF_RAMADAN_NOTIFICATION_SOUND_ON, value)
        }


    var sehriOrifterAlertVibrationOn: Boolean
        get() = preferences.getBoolean(PREF_RAMADAN_NOTIFICATION_VIBRATION_ON, false)
        set(value) = preferences.edit {
            it.putBoolean(PREF_RAMADAN_NOTIFICATION_VIBRATION_ON, value)
        }
    var notificationflag: Boolean
        get() = preferences.getBoolean(NOTIFICATION_FLAG.first, NOTIFICATION_FLAG.second)
        set(value) = preferences.edit {
            it.putBoolean(NOTIFICATION_FLAG.first, value)
        }

    var language: String
        get() = preferences.getString(SET_LANGUAGE.first, SET_LANGUAGE.second)!!
        set(value) = preferences.edit { it.putString(SET_LANGUAGE.first, value) }

    var totalCount: Int
        get() = preferences.getInt(TOTAL_COUNT.first, TOTAL_COUNT.second)
        set(value) = preferences.edit {
            it.putInt(TOTAL_COUNT.first, value)
        }

    fun clearTotalCount() {
        preferences.edit {
            it.remove(TOTALCOUNTTAG)
        }
    }

    fun saveTashbihCount(value: Int, tag: String) {
        preferences.edit {
            it.putInt(tag, value)
        }
    }

    fun loadTashbihCount(tag: String): Int {
        return preferences.getInt(tag, 0)
    }

    fun clearHistoryCount(tag: String) {
        preferences.edit {
            it.remove(tag)
        }
    }

    var soundflag: Boolean
        get() = preferences.getBoolean(SOUND_FLAG.first, SOUND_FLAG.second)
        set(value) = preferences.edit {
            it.putBoolean(SOUND_FLAG.first, value)
        }
}