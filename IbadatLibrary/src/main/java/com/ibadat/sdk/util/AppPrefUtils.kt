package com.ibadat.sdk.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import com.ibadat.sdk.data.manager.prefs.AppPreference
import com.ibadat.sdk.data.model.app_model.DivisionModel
import com.ibadat.sdk.data.model.app_model.MonthModel
import com.ibadat.sdk.data.model.app_model.WeekModel
import java.text.SimpleDateFormat
import java.util.*

internal object AppPrefUtils {
    internal fun getDivision(): MutableList<DivisionModel> {
        val listDivision = mutableListOf<DivisionModel>()
        listDivision.add(DivisionModel(1, "ঢাকা বিভাগ", "", "23.810332", "90.412518"))
        listDivision.add(DivisionModel(2, "খুলনা বিভাগ", "", "22.845641", "89.540328"))
        listDivision.add(DivisionModel(3, "চট্টগ্রাম বিভাগ", "", "22.356851", "91.783182"))
        listDivision.add(DivisionModel(4, "রাজশাহী বিভাগ", "", "24.363589", "88.624135"))
        listDivision.add(DivisionModel(5, "বরিশাল বিভাগ", "", "22.701002", "90.353451"))
        listDivision.add(DivisionModel(6, "সিলেট বিভাগ", "", "24.894929", "91.868706"))
        listDivision.add(DivisionModel(7, "ময়মনসিংহ বিভাগ", "", "24.747221", "90.409756"))
        return listDivision
    }

    private fun getMonth(): MutableList<MonthModel> {
        val listMonth = mutableListOf<MonthModel>()
        listMonth.add(MonthModel(0, "", ""))
        listMonth.add(MonthModel(1, "জানুয়ারি", ""))
        listMonth.add(MonthModel(2, "ফেব্রুয়ারি", ""))
        listMonth.add(MonthModel(3, "মার্চ", ""))
        listMonth.add(MonthModel(4, "এপ্রিল", ""))
        listMonth.add(MonthModel(5, "মে", ""))
        listMonth.add(MonthModel(6, "জুন", ""))
        listMonth.add(MonthModel(7, "জুলাই", ""))
        listMonth.add(MonthModel(8, "আগস্ট", ""))
        listMonth.add(MonthModel(9, "সেপ্টেম্বর", ""))
        listMonth.add(MonthModel(10, "অক্টোবর", ""))
        listMonth.add(MonthModel(11, "নভেম্বর", ""))
        listMonth.add(MonthModel(12, "ডিসেম্বর", ""))
        return listMonth
    }

    private fun getWeekDay(): MutableList<WeekModel> {
        return mutableListOf<WeekModel>().apply {
            add(WeekModel(1, "রবিবার", ""))
            add(WeekModel(2, "সোমবার", ""))
            add(WeekModel(3, "মঙ্গলবার", ""))
            add(WeekModel(4, "বুধবার", ""))
            add(WeekModel(5, "বৃহস্পতিবার", ""))
            add(WeekModel(6, "শুক্রবার", ""))
            add(WeekModel(7, "শনিবার", ""))
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentMonth(): String {
        return SimpleDateFormat("MMM").format(Date())
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDateAndWeek(context: Context): String {
//Bangla
        if (AppPreference.language == "bn") {
            val calIns = Calendar.getInstance()
            return getBanglaWeekName(
                (calIns.get(Calendar.DAY_OF_WEEK)),
            ) + ", " + LanguageConverter.getNumberByLocale(
                calIns.get(Calendar.DATE).toString() + " "
                        + getBanglaMonthNameByMonthNumber(calIns.get(Calendar.MONTH)) + " "
                        + LanguageConverter.convertToNumber(calIns.get(Calendar.YEAR).toString())
            )
        } else {
//English
            val dateIns = Date()
            return SimpleDateFormat("EEEE").format(dateIns) + ", " + SimpleDateFormat("dd MMM yyyy").format(
                dateIns
            )
        }
    }

    fun getBanglaWeekName(weekId: Int): String {
        val weekBanglaName = getWeekDay()
        return if (weekBanglaName.size > weekId) {
            weekBanglaName[weekId].weekBanglaName
        } else ""
    }

    fun getBanglaMonthNameByMonthNumber(monthSerialNumber: Int): String {
        val monthName = getMonth()
        return if (monthName.size > monthSerialNumber) {
            monthName[monthSerialNumber].monthBanglaName
        } else ""
    }

}
