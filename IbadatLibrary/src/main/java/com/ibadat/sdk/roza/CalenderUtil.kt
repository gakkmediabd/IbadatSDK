package com.ibadat.sdk.roza


import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import com.ibadat.sdk.util.HIZRI_YR_OFFSET
import com.ibadat.sdk.util.TWENTY_FOUR_HOURS_IN_MS

import java.text.SimpleDateFormat
import java.util.*


internal object CalenderUtil {
    /**
     * @param:milliseconds(Long)
     * @return DAY_OF_WEEK for current MONTH in GEORGIAN Calender
     */
    fun getDayOfWeek(ms: Long): Int {
        val gCal = GregorianCalendar()
        gCal.timeInMillis = ms
        return gCal.get(Calendar.DAY_OF_WEEK)
    }

    /**
     * @param:milliseconds(Long)
     * @return DAY_OF_MONTH for current MONTH in GEORGIAN Calender
     */
    fun getGrgDayOfCurrentMonth(ms: Long): Int {
        val dateStr = TimeFormtter.getddMMYYYYFormattedStringFromMS(ms)
        val dateStrSplitted = dateStr!!.split("-")
        return dateStrSplitted[0].toInt()
    }

    /**
     * @param:milliseconds(Long)
     * @return DAY_OF_MONTH for Tomorrow in GEORGIAN Calender
     */
    fun getGrgTomorrow(ms: Long): Int {
        val dateStr = TimeFormtter.getddMMYYYYFormattedStringFromMS(ms + TWENTY_FOUR_HOURS_IN_MS)
        val dateStrSplitted = dateStr!!.split("-")
        return dateStrSplitted[0].toInt()
    }

    /**
     * @param:milliseconds(Long)
     * @return DAY_OF_MONTH for current MONTH in HIZRI Calender
     */
    fun getHzrDayOfCurrentMonth(ms: Long): Int {
        val gCal = GregorianCalendar()
        gCal.timeInMillis = ms
        val uCal = UmmalquraCalendar()
        uCal.time = gCal.time
        return uCal[Calendar.DAY_OF_MONTH]
    }

    /**
     * returns corresponding date(milliseconds) in Georgian Calender  for First of Ramadan
     */
    fun getFirstRmdnGrgMs(): Long {
        val uCal = UmmalquraCalendar(getCurrentHizriYear(), UmmalquraCalendar.RAMADHAN, 1)
        val gCal = GregorianCalendar()
        gCal.time = uCal.time
        return gCal.timeInMillis
    }

    //provides current Hizri Year From current Time
    private fun getCurrentHizriYear(): Int {
        val date = TimeFormtter.getddMMYYYYFormattedStringFromMS(System.currentTimeMillis())
        val splitDate = date?.split("-")
        val year = splitDate!![2]
        return year.toInt() - HIZRI_YR_OFFSET
    }

    fun isRamadanNow(): Boolean {
        val uCal = UmmalquraCalendar()
        return uCal[Calendar.MONTH] == UmmalquraCalendar.RAMADHAN
    }

    fun isShabanNow(): Boolean {
        var uCal = UmmalquraCalendar()
        return uCal[Calendar.MONTH] == UmmalquraCalendar.SHAABAN
    }

    fun getDayOfHizriMonth(): Int {
        var uCal = UmmalquraCalendar()
        return uCal[Calendar.DAY_OF_MONTH]
    }

    fun getDayOfHizriMonth2(): Int {
        var uCal = UmmalquraCalendar()
        return uCal[Calendar.DAY_OF_MONTH]
    }

    fun daysLeftTillRamadan(): Long {
        var uCal = UmmalquraCalendar()
        var todayInMs = uCal.timeInMillis
        uCal = UmmalquraCalendar(getCurrentHizriYear(), UmmalquraCalendar.RAMADHAN, 1)
        var firstRamadanInMs = uCal.timeInMillis
        var daysLeftInMs = firstRamadanInMs - todayInMs
        return daysLeftInMs / TWENTY_FOUR_HOURS_IN_MS
    }
}