package com.ibadat.sdk.data.model.roza

import com.ibadat.sdk.R
import com.ibadat.sdk.roza.CalenderUtil
import com.ibadat.sdk.roza.TimeFormtter
import com.ibadat.sdk.util.LanguageConverter
import com.ibadat.sdk.MyApplication

data class Data1(
    val about: Any,
    val createdBy: Any,
    val createdOn: String,
    val id: Any,
    val iftar: String,
    val isActive: Boolean,
    val language: String,
    val order: Int,
    val ramadaDate: String,
    val ramadaDay: String,
    val sehri: String,
    val state: Any,
    val updatedBy: Any,
    val updatedOn: Any
){

    /**
     * Formatted & Localised Sehri time String
     */
    var sehriTimeStr1: String
        get() = LanguageConverter.getDateInBangla(sehri)
        set(value) {
            sehriTimeStr1 = value
        }

    /**
     * Formatted & Localised Ifter time String
     */
    var ifterTimeStr1: String
        get() = LanguageConverter.getDateInBangla(iftar)
        set(value) {
            ifterTimeStr1 = value
        }
    /**
     * returns true when this contents day of month matches todays day of month
     * false otherwise
     */
    var isToday: Boolean
        get() {
            var today = CalenderUtil.getGrgDayOfCurrentMonth(System.currentTimeMillis())
            var date = TimeFormtter.MillisecondFromDateString(ramadaDate)
            var dateMsDay = CalenderUtil.getGrgDayOfCurrentMonth(date!!)
            return today == dateMsDay
        }
        set(value) {
            isToday = value
        }

    var dayOfWeek: String
        get() {
            val date = TimeFormtter.MillisecondFromDateString(ramadaDate)
            val index = CalenderUtil.getDayOfWeek(date!!) - 1
            val daysOfWeek: List<String> =
               MyApplication.getContext().resources.getStringArray(R.array.week_name).toList()
            return daysOfWeek[index]
        }
        set(value) {
            dayOfWeek = value
        }
}