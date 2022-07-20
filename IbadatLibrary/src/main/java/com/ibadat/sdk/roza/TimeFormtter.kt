package com.ibadat.sdk.roza

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.util.Log
import com.ibadat.sdk.azan.Time
import com.ibadat.sdk.R
import com.ibadat.sdk.data.manager.prefs.AppPreference
import com.ibadat.sdk.data.model.app_model.CalenderModel
import com.ibadat.sdk.util.TWELVE_HOURS_IN_MS
import com.ibadat.sdk.util.TWENTY_FOUR_HOURS_IN_MS
import java.io.IOException
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.log

internal object TimeFormtter {

    fun getFormattedTime(inputTime: String): String? {
        val df: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.US)
        val outputformat: DateFormat = SimpleDateFormat("hh:mm:ss aa", Locale.US)
        val date: Date?
        var output: String? = null

        try {
            date = df.parse(inputTime)
            output = outputformat.format(date)
        } catch (e: ParseException) {

        }

        return output
    }

    fun get12HrFormattedStringFrom24HrFormattedString(input: String): String {
        //Date/time pattern of input date
        val df: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
        //Date/time pattern of desired output date
        val outputformat: DateFormat = SimpleDateFormat("hh:mm:ss aa", Locale.ENGLISH)
        var date: Date? = null
        var output: String? = null
        try {
            //Conversion of input String to date
            date = df.parse(input)
            //old date format to new date format
            output = outputformat.format(date!!)
            println(output)
        } catch (pe: ParseException) {
            pe.printStackTrace()
        }
        return output!!
    }

    fun get12HrFormattedhhmmssStringFrom24HrFormattedString(input: String): String {

        //Date/time pattern of input date
        val df: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
        //Date/time pattern of desired output date
        val outputformat: DateFormat = SimpleDateFormat("hh:mm:ss", Locale.ENGLISH)
        var date: Date? = null
        var output: String? = null
        try {
            //Conversion of input String to date
            date = df.parse(input)
            //old date format to new date format
            output = outputformat.format(date)
            println(output)
        } catch (pe: ParseException) {
            pe.printStackTrace()
        }
        return output!!
    }

    fun get12HrFormattedhhmmStringFrom24HrFormattedString(input: String): String {
        //Date/time pattern of input date
        val df: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
        //Date/time pattern of desired output date
        val outputformat: DateFormat = SimpleDateFormat("hh:mm", Locale.ENGLISH)
        var date: Date? = null
        var output: String? = null
        try {
            //Conversion of input String to date
            date = df.parse(input)
            //old date format to new date format
            output = outputformat.format(date)
            println(output)
        } catch (pe: ParseException) {
            pe.printStackTrace()
        }
        return output!!
    }

    fun get12HrFormattedhhStringFrom24HrFormattedString(input: String): String {
        //Date/time pattern of input date
        val df: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
        //Date/time pattern of desired output date
        val outputformat: DateFormat = SimpleDateFormat("hh", Locale.ENGLISH)
        var date: Date? = null
        var output: String? = null
        try {
            //Conversion of input String to date
            date = df.parse(input)
            //old date format to new date format
            output = outputformat.format(date!!)
            println(output)
        } catch (pe: ParseException) {
            pe.printStackTrace()
        }
        return output!!
    }

    fun get12HrFormattedaaaStringFrom24HrFormattedString(input: String): String {
        //Date/time pattern of input date
        val df: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
        //Date/time pattern of desired output date
        val outputformat: DateFormat = SimpleDateFormat("aaa", Locale.ENGLISH)
        var date: Date? = null
        var output: String? = null
        try {
            //Conversion of input String to date
            date = df.parse(input)
            //old date format to new date format
            output = outputformat.format(date!!)
            println(output)
        } catch (pe: ParseException) {
            pe.printStackTrace()
        }
        return output!!
    }


    fun get12HrFormattedTimeFrom24HrFormattedTimeMilli(input: Time): Long {
        //Date/time pattern of input date
        val df: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
        //Date/time pattern of desired output date
        val outputformat: DateFormat = SimpleDateFormat("hh:mm:ss aa", Locale.ENGLISH)
        var date: Date? = null
        var output: String = ""
        var date1: Date? = null
        try {
            val timeStr = input.hour.toString() + ":" + input.minute + ":" + input.second
            //Conversion of input String to date
            date = df.parse(timeStr)
            //old date format to new date format
            output = outputformat.format(date!!)
            date1 = outputformat.parse(output)
            println(output)
        } catch (pe: ParseException) {
            pe.printStackTrace()
        }
        return date1!!.time
    }

    /**
     * @param Time Format day,hour,second
     * @return 'yyyy-MM-dd hh:mm:ss aa' 12Hr Format milli second
     */
    fun get12HrFormattedyyyyMMddhhmmssaaDateTimeFrom24HrFormattedTimeMilli(
        milliseconds: Long,
        input: Time
    ): Long {
        //Date/time pattern of input date
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        //Date/time pattern of desired output date
        val outputformat: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.ENGLISH)
        var date = Date()
        var output: String = ""
        var date1 = Date()
        try {
            val timeStr =
                getyyyyMMddFormattedStringFromMS(milliseconds) + " " + input.hour.toString() + ":" + input.minute + ":" + input.second
            //Conversion of input String to date
            date = df.parse(timeStr)!!

            //old date format to new date format
            output = outputformat.format(
                date
            )
            date1 = outputformat.parse(output)!!
            Log.e("TF", "get12HrTimeMilli: $output")
            println(output)
        } catch (pe: ParseException) {
            pe.printStackTrace()
        }
        return date1.time
    }

    fun getFormattedTimeHourMinute(inputTime: String): String? {
        val df: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.US)
        val outputformat: DateFormat = SimpleDateFormat("hh:mm", Locale.US)
        val date: Date?
        var output: String? = null

        try {
            date = df.parse(inputTime)
            output = outputformat.format(date)
        } catch (e: ParseException) {

        }

        return output
    }

    fun getMsFromHHMMSS(inputTime: String): Long {
        var splittedTime = inputTime.split(":")
        var hrStr = "0"
        var minStr = "0"
        var secStr = "0"
        when (splittedTime.size) {
            3 -> {
                hrStr = splittedTime.get(0)
                minStr = splittedTime.get(1)
                secStr = splittedTime.get(2)
            }
            2 -> {
                minStr = splittedTime.get(0)
                secStr = splittedTime.get(1)
            }
        }
        return (hrStr.toLong() * 3600000) + (minStr.toLong() * 60000) + (secStr.toLong() * 1000)
    }

    fun getDateStringFromMilliSecondsIn12HrFormat(milliseconds: Long): String {
        val date = Date()
        date.time = milliseconds
        val formattedDate = SimpleDateFormat("hh:mm:ss aa", Locale.US).format(date)
        return formattedDate
    }


    fun getTimeStringFromMilliSecondsIn12HrFormatV1(milliseconds: Long): String {
        val date = Date()
        date.time = milliseconds
        val formattedDate = SimpleDateFormat("hh:mm:ss", Locale.US).format(date)
        return formattedDate
    }


    fun getFormetDateFromMiliSecond(milliseconds: Long): String {
        val date = Date()
        date.time = milliseconds
        val formattedDate = SimpleDateFormat("hh:mm:ss aa", Locale.US).format(date)
        return formattedDate
    }

    fun gethhmmssaaMilliFromhhmmssTime(HHmmssTime: Long): Long {
        val formatter = SimpleDateFormat("hh:mm:ss aa")
        formatter.isLenient = false
        val oldDate = formatter.parse(getFormetDateFromMiliSecond(HHmmssTime))
        // val oldMillis = oldDate.time
        val cal = Calendar.getInstance()
        cal.timeInMillis = oldDate!!.time
        return cal.timeInMillis
    }

//    fun getDurationFromMsByLocale(milliseconds: Long): String {
//        val seconds = (milliseconds / 1000).toInt() % 60
//        val minutes = (milliseconds / (1000 * 60) % 60).toInt()
//        val hours = (milliseconds / (1000 * 60 * 60) % 24).toInt()
//        if (hours > 0) {
//            return "${getNumberByLocale(hours.toString())}:${getNumberByLocale(minutes.toString())}:${
//                getNumberByLocale(
//                    seconds.toString()
//                )
//            }"
//        } else {
//            return "${getNumberByLocale(minutes.toString())}:${getNumberByLocale(seconds.toString())}"
//        }
//
//    }

    fun milliSecondsFromTimeString(timeStr: String): Long {
        var milliSec: Long = 0
        val formatter = SimpleDateFormat("hh:mm:ss aa", Locale.ENGLISH)
        try {
            val date = formatter.parse(timeStr)
            milliSec = date!!.time
        } catch (e: Exception) {
//            Log.e("currmili", "chek" + e.message)
        }
        return milliSec
    }

    fun milliSecondsFromTimeStamp(timeStr: Long): Long {
        var milliSec: Long = 0
        val date = Date(timeStr * 1000L) // *1000 is to convert seconds to milliseconds
        val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH) // the format of your date
        sdf.timeZone = TimeZone.getTimeZone("GMT+8")

        val outPut = SimpleDateFormat("hh:mm", Locale.ENGLISH)
        val s = sdf.format(date)
        try {
            val fDate = outPut.parse(s)
            milliSec = fDate.time
        } catch (e: Exception) {
//            Log.e("currmili", "chek" + e.message)
        }
        return milliSec
    }

//    fun get12HrFormateMilliSecondsFromTimeStamp(timeStr: Long): Long {
//        var milliSec: Long = 0
//        val date = Date(timeStr * 1000L) // *1000 is to convert seconds to milliseconds
//        val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH) // the format of your date
//        sdf.timeZone = TimeZone.getTimeZone("GMT+8")
//
//        val outPut = SimpleDateFormat("hh:mm:ss aa", Locale.ENGLISH)
//        val s = sdf.format(date)
//        try {
//            val fDate = outPut.parse(s)
//            milliSec = fDate.time
//        } catch (e: Exception) {
//            Log.e("currmili", "chek" + e.message)
//        }
//        return milliSec
//    }

    fun getTime(ts: Long): String? {
        ///"Asia/Kuala_Lumpur"
        val date = Date(ts * 1000L) // *1000 is to convert seconds to milliseconds
        val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH) // the format of your date
        sdf.timeZone = TimeZone.getTimeZone("GMT+8")
        return sdf.format(date)
    }

    fun getGMT8FromInternationalTime(ts: String): String? {
        ///"Asia/Kuala_Lumpur"
//        val date = Date(ts * 1000L) // *1000 is to convert seconds to milliseconds
        val date = Date()
        val strDate: String
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH) // the format of your date
        sdf.timeZone = TimeZone.getTimeZone("Asia/Dhaka");
        val gmt = sdf.parse(ts)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        strDate = sdf.format(gmt!!);
        return strDate
    }

    fun milliSecondsFromTime(timeStr: String): Long {
        var milliSec: Long = 0
        val formatter = SimpleDateFormat("hh:mm:ss", Locale.US)
        try {
            val date = formatter.parse(timeStr)
            milliSec = date.time
        } catch (e: Exception) {
//            Log.e("currmili", "chek" + e.message)
        }
        return milliSec
    }

    fun getDateStringFromMilliSecondsIn12HrFormatV2(milliseconds: Long): String {
        val date = Date()
        date.time = milliseconds
        return SimpleDateFormat("hh:mm aa", Locale.US).format(date)
    }

    fun getHHMMSSFormattedString(milliSeconds: Long): String {
        return String.format(
            Locale.ENGLISH, "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(milliSeconds),
            TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(
                    milliSeconds
                )
            ),
            TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(
                    milliSeconds
                )
            )
        )
    }

    fun getMMSSFormattedString(milliSeconds: Long): String {
        return String.format(
            Locale.ENGLISH, "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(
                    milliSeconds
                )
            ),
            TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(
                    milliSeconds
                )
            )
        )
    }

    fun milliSecondsFromTimeStringV2(timeStr: String): Long {
        var milliSec: Long = 0
        val formatter = SimpleDateFormat("hh:mm aa")
        // formatter.setLenient(false);
        try {
            val date = formatter.parse(timeStr)
            milliSec = date!!.time
        } catch (e: java.lang.Exception) {
        }
        return milliSec
    }

    fun milliSecondsFromTimeStringV3(timeStr: String): Long {

        var milliSec: Long = 0
        val formatter = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)
        // formatter.setLenient(false);
        try {
            val date = formatter.parse(timeStr)
            milliSec = date!!.time
        } catch (e: java.lang.Exception) {
        }

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSec
        var hr = calendar[Calendar.HOUR_OF_DAY]
        var min = calendar[Calendar.MINUTE]
        var seconds = calendar[Calendar.SECOND]
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = hr
        calendar[Calendar.MINUTE] = min
        calendar[Calendar.SECOND] = seconds
        return calendar.timeInMillis

    }

    fun milliSecondsFromTimeStringV4(timeStr: String): Long {
        var milliSec: Long = 0
        val formatter = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)
        // formatter.setLenient(false);
        try {
            val date = formatter.parse(timeStr)
            milliSec = date.time
        } catch (e: java.lang.Exception) {
        }

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSec
        val hr = calendar[Calendar.HOUR_OF_DAY]
        val min = calendar[Calendar.MINUTE]
        val seconds = calendar[Calendar.SECOND]
        calendar.timeInMillis = System.currentTimeMillis() + TWENTY_FOUR_HOURS_IN_MS
        calendar[Calendar.HOUR_OF_DAY] = hr
        calendar[Calendar.MINUTE] = min
        calendar[Calendar.SECOND] = seconds
        return calendar.timeInMillis
    }

    @SuppressLint("SimpleDateFormat")
    fun milliSecondsFromTimeStringV5(timeStr: String): Long {
        var milliSec: Long = 0
        val formatter = SimpleDateFormat("hh:mm aa")
        // formatter.setLenient(false);
        try {
            val date = formatter.parse(timeStr)
            milliSec = date.time
        } catch (e: java.lang.Exception) {
        }

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSec
        val hr = calendar[Calendar.HOUR_OF_DAY]
        val min = calendar[Calendar.MINUTE]
        val seconds = calendar[Calendar.SECOND]
        calendar.timeInMillis = System.currentTimeMillis() - TWENTY_FOUR_HOURS_IN_MS
        calendar[Calendar.HOUR_OF_DAY] = hr
        calendar[Calendar.MINUTE] = min
        calendar[Calendar.SECOND] = seconds
        return calendar.timeInMillis
    }

//    fun getCalenderFromyyyyMMddTHHmmssMs(milliseconds: Long): Calendar {
//        val calender = Calendar.getInstance()
//        val formatter = SimpleDateFormat("hh:mm aa").parse()
//        getCalenderFromyyyyMMddTHHmmssMs()
//        calender[Calendar.HOUR_OF_DAY] = 0
//        calender[Calendar.MINUTE] = 0
//        calender[Calendar.SECOND] = 0
//        calender[Calendar.MILLISECOND] = 0
//
//        return calender
//    }

    fun milliSecondsForTodayFomMs(milliseconds: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliseconds
        var hr = calendar[Calendar.HOUR_OF_DAY]
        var min = calendar[Calendar.MINUTE]
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = hr
        calendar[Calendar.MINUTE] = min
        Log.e("TF", "milliSecondsForTodayFomMs: $hr$min")
        return calendar.timeInMillis

    }

    fun milliSecondsFromTimeStringForToday(timeStr: String?): Long {
        var milliSec: Long = 0
        val formatter = SimpleDateFormat("hh:mm aa")
        // formatter.setLenient(false);
        try {
            val date = formatter.parse(timeStr)
            val calendar = Calendar.getInstance()
            calendar.time = date
            val hr = calendar[Calendar.HOUR_OF_DAY]
            val min = calendar[Calendar.MINUTE]
            calendar.timeInMillis = System.currentTimeMillis()
            calendar[Calendar.HOUR_OF_DAY] = hr
            calendar[Calendar.MINUTE] = min
            milliSec = calendar.timeInMillis
        } catch (e: java.lang.Exception) {
        }
        return milliSec
    }

    fun getCurrentTimeWithMillisecond(): Long {
        return System.currentTimeMillis()
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentMilliSecondAsHHmmss(): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = calendar[Calendar.HOUR_OF_DAY]
        calendar[Calendar.MINUTE] = calendar[Calendar.MINUTE]
        calendar[Calendar.SECOND] = calendar[Calendar.SECOND]
        return calendar.timeInMillis
    }

    fun getCurrentDateAsyyyyMMdd(): String {
        val strDate: String
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        strDate = sdf.format(Date())
        return strDate
    }

    fun getDateInMillis(day: Int, month: Int, year: Int): Long {
        val calendar = Calendar.getInstance()
        calendar[year, month] = day
        return calendar.timeInMillis
    }

    /**
     * @param long pass any date time milli sec
     * @return yyyy-MM-dd formed date
     */
    @SuppressLint("SimpleDateFormat")
    fun getDateFromMilliSecond(milli: Long): String {
        return SimpleDateFormat("yyyy-MM-dd").format(milli)
    }

    fun get12HrCurrenthhmmssMilliSecond(): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = calendar[Calendar.HOUR_OF_DAY]
        calendar[Calendar.MINUTE] = calendar[Calendar.MINUTE]
        calendar[Calendar.SECOND] = calendar[Calendar.SECOND]
        return gethhmmssaaMilliFromhhmmssTime(calendar.timeInMillis)
    }


    fun getCurrentMilliSecondAshhmmss(): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = calendar[Calendar.HOUR_OF_DAY]
        calendar[Calendar.MINUTE] = calendar[Calendar.MINUTE]
        calendar[Calendar.SECOND] = calendar[Calendar.SECOND]

        return milliSecondsFromTimeStamp(calendar.timeInMillis)
    }

    fun getCurrentTimehhmmaa(): String {
        val outputFormat: DateFormat = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)
        var output = ""
        try {
            output = outputFormat.format(Date())
        } catch (pe: ParseException) {
            pe.printStackTrace()
        }
        return output
    }

    fun getCurrentDay(): Int {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    }

    fun getCurrentSecond(): Int {
        return Calendar.getInstance().get(Calendar.SECOND)
    }

    fun getCurrentCalenderDate(): Calendar {
        return Calendar.getInstance().apply {
            get(Calendar.DATE)
        }
    }

    fun getCurrentDate(): Date {
        return Calendar.getInstance().apply {
            get(Calendar.DATE)
        }.time
    }

    fun getCurrentTimeMilliFromDate(): Long {
        return Date().time
    }

    fun getMilliSecondsFromHHmmss(mTime: Time): Long {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR] = mTime.hour
        calendar[Calendar.MINUTE] = mTime.minute
        calendar[Calendar.SECOND] = mTime.second
        return calendar.timeInMillis
    }

    fun milliSecondsFromTimeStringForTodayV2(timeStr: String): Long {
        var milliSec: Long = 0
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.US)
        // formatter.setLenient(false);
        try {
            val date = formatter.parse(timeStr)
            val calendar = Calendar.getInstance()
            calendar.time = date
            val hr = calendar[Calendar.HOUR_OF_DAY]
            val min = calendar[Calendar.MINUTE]
            calendar.timeInMillis = System.currentTimeMillis()
            calendar[Calendar.HOUR_OF_DAY] = hr
            calendar[Calendar.MINUTE] = min
            milliSec = calendar.timeInMillis

        } catch (e: Exception) {
            Log.e("cannot", "convert")
        }
        return milliSec
    }

    fun MillisecondFromDateString(date1: String): Long? {
        val myDate = date1

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val date = sdf.parse(myDate)
        val timeInMillis = date.time
        return timeInMillis
    }

    /**
     * @param long pass any date time milli sec
     * @return yyyy-MM-dd formed milli sec
     */
    @SuppressLint("SimpleDateFormat")
    fun getMillisecondFromDate(date1: String): Long {
        val myDate = date1
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.parse(myDate)
        val timeInMillis = date!!.time
        return timeInMillis
    }

    @SuppressLint("SimpleDateFormat")
    fun getMilliSecondFromHHmmss(longTime: String): String {
        val sdf = SimpleDateFormat("HH:mm:ss")
        val date = sdf.parse(longTime)
        return date.time.toString()
    }

    fun getddMMYYYYFormattedStringFromMS(milliseconds: Long): String? {
        val date = Date()
        date.time = milliseconds
        return SimpleDateFormat("dd-MM-yyyy").format(date)
    }

    fun getyyyyMMddFormattedStringFromMS(milliseconds: Long): String? {
        val date = Date()
        date.time = milliseconds
        return SimpleDateFormat("yyyy-MM-dd").format(date)
    }

    fun getMMMFormattedStringFromMS(milliseconds: Long): String? {
        val date = Date()
        date.time = milliseconds
        return SimpleDateFormat("MMM").format(date)
    }

    fun getMMFormattedStringFromMS(milliseconds: Long): String {
        val date = Date()
        date.time = milliseconds
        return SimpleDateFormat("MM").format(date)
    }

    fun getddMMYYYYHHMMSSFormattedStringFromMS(milliseconds: Long): String? {
        val date = Date()
        date.time = milliseconds
        return SimpleDateFormat("dd-MM-yyyy HH:mm:ss a z").format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun getMillSecondFormattedStringFromTime(
        longTime: Time
    ): String {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR, longTime.hour)
            set(Calendar.MINUTE, longTime.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis.toString()
    }

    fun getMillSecondFromTime(
        longTime: Time
    ): Long {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR, longTime.hour)
            set(Calendar.MINUTE, longTime.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    fun getddMMYYYYHHMMFormattedStringFromMS(milliseconds: Long): String? {
        val date = Date()
        date.time = milliseconds
        return SimpleDateFormat("dd-MM-yyyy HH:mm").format(date)
    }

    fun getTodayDDMMYYYYFormattedStr(): String? {
        val time = System.currentTimeMillis()
        return getddMMYYYYHHMMSSFormattedStringFromMS(time)
    }

//    fun getCurrentTime(): String {
//        val c = Calendar.getInstance()
//        val hours = c[Calendar.HOUR_OF_DAY]
//        val minutes = c[Calendar.MINUTE]
//        val time1 = "$hours:$minutes"
//        val time = "" + getTime12Minute(time1)
//        if (AppPreference.language.equals("bn")) {
//            return time.getNumberInBangla()
//        }
//        return time
//    }

    fun getTime12(time: String): String? {
        var hour12 = ""
        try {
            val _24HourSDF = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            val _12HourSDF = SimpleDateFormat("hh:mm", Locale.ENGLISH)
            val _24HourDt = _24HourSDF.parse(time)
            hour12 = _12HourSDF.format(_24HourDt)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return hour12
    }

    fun getTime12Minute(time: String): String? {
        var hour12 = ""
        try {
            val _24HourSDF = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            val _12HourSDF = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)
            val _24HourDt = _24HourSDF.parse(time)
            hour12 = _12HourSDF.format(_24HourDt)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return hour12
    }

    private fun getAMorPM(hours: Int): String? {
        return if (hours < 5) {
            "AM"
        } else if (hours < 12) {
            "AM"
        } else if (hours < 14) {
            "PM"
        } else if (hours < 18) {
            "PM"
        } else if (hours < 23) {
            "PM"
        } else "AM"
    }

    fun getDhuhrAmOrPm(s: String, context: Context): String? {
        val splitTime = s.split(":".toRegex()).toTypedArray()
        val namazHourFajr = splitTime[0]
        val namazminuteFajr = splitTime[1]

        return if (namazHourFajr == "11" || namazHourFajr == "১১") {
            context.getString(R.string.txt_am)
        } else {
            context.getString(R.string.txt_pm)
        }
    }

    fun getCountryName(context: Context?): String {

        var countryName: String = "BANGLADESH"
        val geocoder = Geocoder(context, Locale.getDefault())
//        val latitude: Double= 23.810331
//        val longitude: Double = 90.412521
        val latitude: Double = AppPreference.getUserCurrentLocation().lat!!
        val longitude: Double = AppPreference.getUserCurrentLocation().lng!!
        try {
            if (geocoder.getFromLocation(latitude, longitude, 1).size != 0) {
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)

                if (addresses.size > 0 && !addresses.isNullOrEmpty()) {
                    countryName = addresses[0].countryName
                    if (countryName != null) {
                        countryName = countryName.uppercase()
                    } else {
                        countryName =
                            "Not Found"
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return countryName
    }

    fun incrementDateByOne(date: Date): Date {
        val c = Calendar.getInstance()
        c.time = date
        c.add(Calendar.DATE, 1)
        Log.e("TF", "incrementDateByOne: " + c[Calendar.DATE])
        return c.time
    }

    fun decrementDateByOne(date: Date): Date {
        val c = Calendar.getInstance()
        c.time = date
        c.add(Calendar.DATE, -1)
        Log.e("TF", "decrementDateByOne: " + c[Calendar.DATE])
        return c.time
    }

    fun getDurationString(sec: Int): String? {
        var seconds = sec
        val hours = seconds / 3600
        val minutes = seconds % 3600 / 60
        seconds = seconds % 60
        val formattedString = when (sec >= 3600) {
            true -> "${twoDigitString(hours)}:${twoDigitString(minutes)}:${twoDigitString(seconds)}"
            else -> "${twoDigitString(minutes)}:${twoDigitString(seconds)}"
        }

        return formattedString
    }

    private fun twoDigitString(number: Int): String {
        if (number == 0) {
            return "00"
        }
        return if (number / 10 == 0) {
            "0$number"
        } else number.toString()
    }


    fun String.toHr(): Int {
        val arr = this.split(":")
        return arr[0].toInt()
    }

    fun String.toMin(): Int {
        val arr = this.split(":")
        return arr[1].toInt()
    }

    fun String.toSec(): Int {
        return "00".toInt()
    }

    fun getCurrentTimeMillihhmmssaa(): Long {
        return TimeFormtter.milliSecondsFromTimeString(
            getDateStringFromMilliSecondsIn12HrFormat(
                getCurrentTimeMilliFromDate()
            )
        )
    }

    fun Long.to24HrFormatMs(): Long {
        var format: DateFormat = SimpleDateFormat("hh:mm a")
        var date = Date()
        date.time = this
        val dateStr = format.format(date)
        if (dateStr.contains("PM")) {
            return this + TWELVE_HOURS_IN_MS
        }
        return this
    }

    fun getCalenderDate(date: Date): CalenderModel {
        val cal = Calendar.getInstance()
            .apply {
                time = date
            }
//    year = cal.get(Calendar.YEAR)
//    month = cal.get(Calendar.MONTH)
//    day = cal.get(Calendar.DATE)
//    Log.d("DateTime", "DateTime:$year month: $month day: $day")
//    weekIndex = cal.get(7) - 1
        return CalenderModel(
            year = cal.get(Calendar.YEAR),
            month = cal.get(Calendar.MONTH) + 1,
            day = cal.get(Calendar.DATE),
            weekId = cal.get(Calendar.DAY_OF_WEEK) - 1
        )
    }

}