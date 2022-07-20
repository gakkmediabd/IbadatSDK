package com.ibadat.sdk.roza


import com.ibadat.sdk.azan.Azan
import com.ibadat.sdk.azan.Method.Companion.KARACHI_HANAF
import com.ibadat.sdk.azan.astrologicalCalc.*
import com.ibadat.sdk.data.manager.prefs.AppPreference
import com.ibadat.sdk.data.model.app_model.CurrentDayAzansModel
import com.ibadat.sdk.data.model.roza.IfterAndSehriTime
import com.ibadat.sdk.util.getGMTOffSet
import java.util.*

internal object AllAzanTimeProvider {

    fun getRojaTimeByDateAndOffsetDay(
        geoLat: Double,
        geoLong: Double,
        date: Date,
        day: Int
    ): IfterAndSehriTime {
        val cal = GregorianCalendar()
        cal.time = date
        val prayerTimes =
            Azan(
                Location(geoLat, geoLong, getGMTOffSet(cal), 0),
                KARACHI_HANAF
            ).getPrayerTimes(
                SimpleDate(
                    cal
                )
            )
        return IfterAndSehriTime(
            cal.timeInMillis,
            prayerTimes.fajr(),
            prayerTimes.maghrib()
        )
    }

    fun get10DaysSehriAndIftarTime(
        geoLat: Double,
        geoLong: Double
    ): MutableList<IfterAndSehriTime> {
        val listISTime: MutableList<IfterAndSehriTime> = mutableListOf()
        var date = TimeFormtter.getCurrentDate()
        for (dayCount in 1..10) {
            date = TimeFormtter.incrementDateByOne(date)
            listISTime.add(
                getRojaTimeByDateAndOffsetDay(
                    geoLat,
                    geoLong, date,
                    dayCount
                )
            )
        }
        return listISTime
    }

    fun getCurrentDateAzanTimeByDate(
        date: Date
    ): CurrentDayAzansModel {
        val cal = GregorianCalendar()
        cal.time = date
        val today = SimpleDate(cal)
        val location = Location(
            AppPreference.lastLatitude.toDouble(),
            AppPreference.lastLongitude.toDouble(),
            getGMTOffSet(cal),
            0
        )
        val prayerTimes = Azan(
            location,
            KARACHI_HANAF
        ).getPrayerTimes(
            today
        )

        return CurrentDayAzansModel(
            indexNumber = 0,
            currentDateTimeMilli = cal.timeInMillis,
            currentDateFajrTime = prayerTimes.fajr(),
            currentDateShuruqTime = prayerTimes.shuruq(),
            currentDateDhuhrTime = prayerTimes.thuhr(),
            currentDateAsrTime = prayerTimes.assr(),
            currentDateMaghribTime = prayerTimes.maghrib(),
            currentDateIshaaTime = prayerTimes.ishaa()
        )
    }
}