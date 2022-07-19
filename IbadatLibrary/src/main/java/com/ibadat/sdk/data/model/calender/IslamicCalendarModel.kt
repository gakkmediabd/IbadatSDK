package com.ibadat.sdk.data.model.calender



data class IslamicCalendarModel(
    private val str: String,
) {
    var dayTxt: String = str
    var isToday: Boolean = false
}
