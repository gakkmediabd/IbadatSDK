package com.ibadat.sdk.data.model.roza


import com.ibadat.sdk.R
import com.ibadat.sdk.roza.CalenderUtil
import com.ibadat.sdk.util.LanguageConverter
import com.ibadat.sdk.MyApplication
import com.ibadat.sdk.util.in12HrFormat
import com.ibadat.sdk.azan.*

data class IfterAndSehriTime(
    val dateMs: Long,
    val sehriTIme: Time,
    val ifterTime: Time
) {


    override fun toString(): String {
        return "DayOfMon:${CalenderUtil.getGrgDayOfCurrentMonth(dateMs)} S:$sehriTIme I:$ifterTime"
    }

    /**
     * Formatted & Localised Sehri time String
     */
    var sehriTimeStr: String
        get() = "${
            LanguageConverter.getDateInBangla(sehriTIme.hour.in12HrFormat().toString())
        }:${
            LanguageConverter.getDateInBangla(
                sehriTIme.minute.toString()
            )
        }"
        set(value) {
            sehriTimeStr = value
        }
    var sehriTimeStrEnglish: String
        get() = "${
            sehriTIme.hour.in12HrFormat().toString()
        }:${
            sehriTIme.minute.toString()
        }"
        set(value) {
            sehriTimeStrEnglish = value
        }

    /**
     * Formatted & Localised Ifter time String
     */
    var ifterTimeStr: String
        get() = "${LanguageConverter.getDateInBangla(ifterTime.hour.in12HrFormat().toString())}:${
            LanguageConverter.getDateInBangla(
                ifterTime.minute.toString()
            )
        }"
        set(value) {
            ifterTimeStr = value
        }

    /**
     * Formatted & Localised Ifter time String
     */
    var ifterTimeStrEnglish: String
        get() = "${ifterTime.hour.in12HrFormat().toString()}:${

            ifterTime.minute.toString()
        }"
        set(value) {
            ifterTimeStrEnglish = value
        }

    /**
     * returns true when this contents day of month matches todays day of month
     * false otherwise
     */
    var isToday: Boolean
        get() {
            var today = CalenderUtil.getGrgDayOfCurrentMonth(System.currentTimeMillis())
            var dateMsDay = CalenderUtil.getGrgDayOfCurrentMonth(dateMs)
            return today == dateMsDay
        }
        set(value) {
            isToday = value
        }

    /**
     * returns true when this contents day of month matches tomorrows day of month
     * false otherwise
     */
    var isTomorrow: Boolean
        get() {
            var tomorrow = CalenderUtil.getGrgTomorrow(System.currentTimeMillis())
            var dateMsDay = CalenderUtil.getGrgDayOfCurrentMonth(dateMs)
            return tomorrow == dateMsDay
        }
        set(value) {
            isTomorrow = value
        }
    var dayOfGeorgianMonthInEnglish: String
        get() =
            CalenderUtil.getGrgDayOfCurrentMonth(dateMs).toString()
        set(value) {
            dayOfGeorgianMonthInEnglish = value
        }

    var dayOfGeorgianMonth: String
        get() = LanguageConverter.getNumberByLocale(
            CalenderUtil.getGrgDayOfCurrentMonth(dateMs).toString()
        )
        set(value) {
            dayOfGeorgianMonth = value
        }

    var dayOfHizriMonth: String
        get() = LanguageConverter.getDateInBangla(
            CalenderUtil.getHzrDayOfCurrentMonth(dateMs).toString()
        )
        set(value) {
            dayOfGeorgianMonth = value
        }

    var dayOfWeek: String
        get() {
            val index = CalenderUtil.getDayOfWeek(dateMs) - 1
            val daysOfWeek: List<String> =
                MyApplication.getContext().resources.getStringArray(R.array.week_name_bangla)
                    .toList()
            return daysOfWeek[index]
        }
        set(value) {

            dayOfWeek = value
        }
}


