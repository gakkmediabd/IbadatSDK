package com.ibadat.sdk.util

import com.ibadat.sdk.azan.Azan
import com.ibadat.sdk.azan.*
import com.ibadat.sdk.azan.astrologicalCalc.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun getTomorrowAzanTimes(lat: Double, lang: Double): AzanTimes {
    val cal = GregorianCalendar()
    cal.add(Calendar.DATE, 1)
    val tomorrow = SimpleDate(cal)
    val location = Location(lat, lang, getGMTOffSet(cal), 0)
    val azan = Azan(location, Method.KARACHI_HANAF)
    return azan.getPrayerTimes(tomorrow)

}

fun getGMTOffSet(cal: Calendar): Double {
    return try {
        val date: DateFormat = SimpleDateFormat("z", Locale.US)
        val gmt: String = date.format(cal.time)
        gmt.replace(Regex("""[+,-,:00,:30,GMT]"""), "").toDouble()
    } catch (e: Exception) {
        2.00;
    }
}

