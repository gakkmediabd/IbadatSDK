package com.ibadat.sdk.data.model.app_model

import android.content.Context
import com.azan.Time
import com.ibadat.sdk.R
import com.ibadat.sdk.data.manager.prefs.AppPreference
import com.ibadat.sdk.roza.TimeFormtter
import com.ibadat.sdk.util.*

data class CurrentDayAzansModel(
    val indexNumber: Int,
    var currentDateTimeMilli: Long,
    var currentDateFajrTime: Time,
    var currentDateShuruqTime: Time,
    var currentDateDhuhrTime: Time,
    var currentDateAsrTime: Time,
    var currentDateMaghribTime: Time,
    var currentDateIshaaTime: Time
) {
    var allWaqtOverForTOday = false

    var timeLeft = ""

    fun getCurrentDateAzan(context: Context): DueAzanModel {
        val dueAzanModel: DueAzanModel
        val azanTimesTomorrow = AzanTimeFormatted(
            getTomorrowAzanTimes(
                AppPreference.lastLatitude.toDouble(),
                AppPreference.lastLongitude.toDouble()
            )
        )
        val curMillis = getCurrentTimeMillihhmmssaa()

        if (curMillis < TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                currentDateFajrTime
            )
        ) {
            dueAzanModel = DueAzanModel(
                context.getString(R.string.fojor_ar_okto),
                currentStartedAzan12HrTime =
                TimeFormtter.getDateStringFromMilliSecondsIn12HrFormatV2(
                    TimeFormtter.milliSecondsFromTimeString(
                        TimeFormtter.get12HrFormattedStringFrom24HrFormattedString(
                            currentDateIshaaTime.toString()
                        )
                    )
                ),
                currentStartedAzan12HrTimeMilliSecond =
                TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                    currentDateIshaaTime
                ),
                currentStartedAzan24HrTime = hr24FormattedTimeString(currentDateIshaaTime),
                currentStartedAzan24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateIshaaTime
                ),
                nextAzan12HrTime = TimeFormtter.getDateStringFromMilliSecondsIn12HrFormatV2(
                    TimeFormtter.milliSecondsFromTimeString(
                        TimeFormtter.get12HrFormattedStringFrom24HrFormattedString(
                            currentDateFajrTime.toString()
                        )
                    )
                ),
                nextAzan24HrTime = hr24FormattedTimeString(currentDateFajrTime),
                nextAzan12HrTimeMilliSecond = TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                    currentDateFajrTime
                ),
                nextAzan24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateFajrTime
                ),
                sunrise12HrTime = hr12FormattedBanglaTimehhmmaaString(currentDateShuruqTime),
                sunrise24HrTime = hr24FormattedTimeString(currentDateShuruqTime),
                sunrise12HrTimeMilliSecond =
                TimeFormtter.getMillSecondFromTime(
                    currentDateShuruqTime
                ),
                sunrise24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateShuruqTime
                ),
                context.getString(R.string.baki_ase),
                isNotification = false,
                dayTypeName = context.getString(R.string.vor),
                meridiemType = TimeFormtter.get12HrFormattedaaaStringFrom24HrFormattedString(
                    hr24FormattedTimeString(currentDateFajrTime)
                )
            )
        } else if (curMillis < TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                currentDateDhuhrTime
            )
        ) {
            dueAzanModel = DueAzanModel(
                context.getString(R.string.dhor_ar_okto),
                currentStartedAzan12HrTime = TimeFormtter.getDateStringFromMilliSecondsIn12HrFormatV2(
                    TimeFormtter.milliSecondsFromTimeString(
                        TimeFormtter.get12HrFormattedStringFrom24HrFormattedString(
                            currentDateFajrTime.toString()
                        )
                    )
                ),
                currentStartedAzan12HrTimeMilliSecond = TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                    currentDateFajrTime
                ),
                currentStartedAzan24HrTime = hr24FormattedTimeString(currentDateFajrTime),
                currentStartedAzan24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateFajrTime
                ),
                nextAzan12HrTime = TimeFormtter.getDateStringFromMilliSecondsIn12HrFormatV2(
                    TimeFormtter.milliSecondsFromTimeString(
                        TimeFormtter.get12HrFormattedStringFrom24HrFormattedString(
                            currentDateDhuhrTime.toString()
                        )
                    )
                ),
                nextAzan24HrTime = hr24FormattedTimeString(currentDateDhuhrTime),
                nextAzan12HrTimeMilliSecond = TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                    currentDateDhuhrTime
                ),
                nextAzan24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateDhuhrTime
                ),
                sunrise12HrTime = hr12FormattedBanglaTimehhmmaaString(currentDateShuruqTime),
                sunrise24HrTime = hr24FormattedTimeString(currentDateShuruqTime),
                sunrise12HrTimeMilliSecond = TimeFormtter.getMillSecondFromTime(
                    currentDateShuruqTime
                ),
                sunrise24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateShuruqTime
                ),
                context.getString(R.string.baki_ase),
                isNotification = false,
                dayTypeName = context.getString(R.string.dupor),
                meridiemType = TimeFormtter.get12HrFormattedaaaStringFrom24HrFormattedString(
                    hr24FormattedTimeString(currentDateDhuhrTime)
                )
            )
        } else if (curMillis < TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                currentDateAsrTime
            )
        ) {
            dueAzanModel = DueAzanModel(
                context.getString(R.string.asor_ar_okto),
                currentStartedAzan12HrTime = TimeFormtter.getDateStringFromMilliSecondsIn12HrFormatV2(
                    TimeFormtter.milliSecondsFromTimeString(
                        TimeFormtter.get12HrFormattedStringFrom24HrFormattedString(
                            currentDateDhuhrTime.toString()
                        )
                    )
                ),
                currentStartedAzan12HrTimeMilliSecond = TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                    currentDateDhuhrTime
                ),
                currentStartedAzan24HrTime = hr24FormattedTimeString(currentDateDhuhrTime),
                currentStartedAzan24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateDhuhrTime
                ),
                nextAzan12HrTime = TimeFormtter.getDateStringFromMilliSecondsIn12HrFormatV2(
                    TimeFormtter.milliSecondsFromTimeString(
                        TimeFormtter.get12HrFormattedStringFrom24HrFormattedString(
                            currentDateAsrTime.toString()
                        )
                    )
                ),
                nextAzan24HrTime = hr24FormattedTimeString(currentDateAsrTime),
                nextAzan12HrTimeMilliSecond = TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                    currentDateAsrTime
                ),
                nextAzan24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateAsrTime
                ),
                sunrise12HrTime = hr12FormattedBanglaTimehhmmaaString(currentDateShuruqTime),
                sunrise24HrTime = hr24FormattedTimeString(currentDateShuruqTime),
                sunrise12HrTimeMilliSecond = TimeFormtter.getMillSecondFromTime(
                    currentDateShuruqTime
                ),
                sunrise24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateShuruqTime
                ),
                context.getString(R.string.baki_ase),
                isNotification = false,
                dayTypeName = context.getString(R.string.bikal),
                meridiemType = TimeFormtter.get12HrFormattedaaaStringFrom24HrFormattedString(
                    hr24FormattedTimeString(currentDateAsrTime)
                )
            )
        } else if (curMillis < TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                currentDateMaghribTime
            )
        ) {
            dueAzanModel = DueAzanModel(
                context.getString(R.string.magrib_ar_okto),
                currentStartedAzan12HrTime = TimeFormtter.getDateStringFromMilliSecondsIn12HrFormatV2(
                    TimeFormtter.milliSecondsFromTimeString(
                        TimeFormtter.get12HrFormattedStringFrom24HrFormattedString(
                            currentDateAsrTime.toString()
                        )
                    )
                ),
                currentStartedAzan12HrTimeMilliSecond = TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                    currentDateAsrTime
                ),
                currentStartedAzan24HrTime = hr24FormattedTimeString(currentDateAsrTime),
                currentStartedAzan24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateAsrTime
                ),
                nextAzan12HrTime = TimeFormtter.getDateStringFromMilliSecondsIn12HrFormatV2(
                    TimeFormtter.milliSecondsFromTimeString(
                        TimeFormtter.get12HrFormattedStringFrom24HrFormattedString(
                            currentDateMaghribTime.toString()
                        )
                    )
                ),
                nextAzan24HrTime = hr24FormattedTimeString(currentDateMaghribTime),
                nextAzan12HrTimeMilliSecond = TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                    currentDateMaghribTime
                ),
                nextAzan24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateMaghribTime
                ),
                sunrise12HrTime = hr12FormattedBanglaTimehhmmaaString(currentDateShuruqTime),
                sunrise24HrTime = hr24FormattedTimeString(currentDateShuruqTime),
                sunrise12HrTimeMilliSecond = TimeFormtter.getMillSecondFromTime(
                    currentDateShuruqTime
                ),
                sunrise24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateShuruqTime
                ),
                context.getString(R.string.baki_ase),
                isNotification = false,
                dayTypeName = context.getString(R.string.sondha),
                meridiemType = TimeFormtter.get12HrFormattedaaaStringFrom24HrFormattedString(
                    hr24FormattedTimeString(currentDateMaghribTime)
                )
            )
        } else if (curMillis < TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                currentDateIshaaTime
            )
        ) {
            dueAzanModel = DueAzanModel(
                context.getString(R.string.isha_ar_okto),
                currentStartedAzan12HrTime = TimeFormtter.getDateStringFromMilliSecondsIn12HrFormatV2(
                    TimeFormtter.milliSecondsFromTimeString(
                        TimeFormtter.get12HrFormattedStringFrom24HrFormattedString(
                            currentDateMaghribTime.toString()
                        )
                    )
                ),
                currentStartedAzan12HrTimeMilliSecond = TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                    currentDateMaghribTime
                ),
                currentStartedAzan24HrTime = hr24FormattedTimeString(currentDateMaghribTime),
                currentStartedAzan24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateMaghribTime
                ),
                nextAzan12HrTime = TimeFormtter.getDateStringFromMilliSecondsIn12HrFormatV2(
                    TimeFormtter.milliSecondsFromTimeString(
                        TimeFormtter.get12HrFormattedStringFrom24HrFormattedString(
                            currentDateIshaaTime.toString()
                        )
                    )
                ),
                nextAzan24HrTime = hr24FormattedTimeString(currentDateIshaaTime),
                nextAzan12HrTimeMilliSecond =
                TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                    currentDateIshaaTime
                ),
                nextAzan24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateIshaaTime
                ),
                sunrise12HrTime = hr12FormattedBanglaTimehhmmaaString(currentDateShuruqTime),
                sunrise24HrTime = hr24FormattedTimeString(currentDateShuruqTime),
                sunrise12HrTimeMilliSecond = TimeFormtter.getMillSecondFromTime(
                    currentDateShuruqTime
                ),
                sunrise24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateShuruqTime
                ),
                context.getString(R.string.baki_ase),
                isNotification = false,
                dayTypeName = context.getString(R.string.rat),
                meridiemType = TimeFormtter.get12HrFormattedaaaStringFrom24HrFormattedString(
                    hr24FormattedTimeString(currentDateIshaaTime)
                )
            )
        } else {
            allWaqtOverForTOday = true
            dueAzanModel = DueAzanModel(
                nextAzanName = context.getString(R.string.fojor_ar_okto),
                currentStartedAzan12HrTime = TimeFormtter.getDateStringFromMilliSecondsIn12HrFormatV2(
                    TimeFormtter.milliSecondsFromTimeString(
                        TimeFormtter.get12HrFormattedStringFrom24HrFormattedString(
                            currentDateIshaaTime.toString()
                        )
                    )
                ),
                currentStartedAzan12HrTimeMilliSecond =
                TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                    currentDateIshaaTime
                ),
                currentStartedAzan24HrTime = hr24FormattedTimeString(currentDateIshaaTime),
                currentStartedAzan24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateIshaaTime
                ),
                nextAzan12HrTime = TimeFormtter.getDateStringFromMilliSecondsIn12HrFormatV2(
                    TimeFormtter.milliSecondsFromTimeString(
                        azanTimesTomorrow.getFajr().toString()
                    )
                ),
                nextAzan24HrTime =
                "00:00:00",
                nextAzan12HrTimeMilliSecond = TimeFormtter.get12HrFormattedTimeFrom24HrFormattedTimeMilli(
                    azanTimesTomorrow.getFajr()
                ),
                nextAzan24HrTimeMilliSecond = 0,
                sunrise12HrTime = hr12FormattedBanglaTimehhmmaaString(currentDateShuruqTime),
                sunrise24HrTime = hr24FormattedTimeString(currentDateShuruqTime),
                sunrise12HrTimeMilliSecond = TimeFormtter.getMillSecondFromTime(
                    currentDateShuruqTime
                ),
                sunrise24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                    currentDateShuruqTime
                ),
                context.getString(R.string.txt_bangla_end),
                isNotification = false,
                dayTypeName = context.getString(R.string.rat),
                meridiemType = TimeFormtter.get12HrFormattedaaaStringFrom24HrFormattedString(
                    hr24FormattedTimeString(currentDateDhuhrTime)
                )
            )
        }

        var timeLeftinMillis = when (allWaqtOverForTOday) {
            true -> dueAzanModel.nextAzan12HrTimeMilliSecond - (getCurrentTimeMillihhmmssaa())
            false -> dueAzanModel.nextAzan12HrTimeMilliSecond - (getCurrentTimeMillihhmmssaa())
        }
        //  Log.i("ASDDA","${TimeFormtter.milliSecondsFromTimeStringV4(upCommingPrayer.nextWaqtTime)} ${upCommingPrayer.nextWaqtTime}")
        if (timeLeftinMillis < 0) {
//            timeLeftinMillis =                TimeFormtter.milliSecondsFromTimeStringV2(dueAzanModel.nextAzan12HrTimeMilliSecond.toString())
            timeLeftinMillis = dueAzanModel.nextAzan12HrTimeMilliSecond
//            timeLeftinMillis + -TWENTY_FOUR_HOURS_IN_MS
            timeLeftinMillis -= curMillis
        }
        timeLeft = TimeFormtter.getHHMMSSFormattedString(timeLeftinMillis)
        return dueAzanModel
    }

    fun getSelectedAzans(context: Context): MutableList<DueAzanModel> {
        return mutableListOf<DueAzanModel>().apply {
            add(
                DueAzanModel(
                    context.getString(R.string.fojor_ar_okto),
                    currentStartedAzan12HrTime = TimeFormtter.getDateFromMilliSecond(
                        TimeFormtter.getMilliSecondsFromHHmmss(
                            currentDateShuruqTime
                        )
                    ),
                    currentStartedAzan12HrTimeMilliSecond = 0,
                    currentStartedAzan24HrTime = "",
                    currentStartedAzan24HrTimeMilliSecond = 0,
                    nextAzan12HrTime = hr12FormattedBanglaTimeString(currentDateFajrTime),
                    nextAzan24HrTime = hr24FormattedTimeString(currentDateFajrTime),
                    nextAzan12HrTimeMilliSecond =
                    TimeFormtter.get12HrFormattedyyyyMMddhhmmssaaDateTimeFrom24HrFormattedTimeMilli(
                        currentDateTimeMilli,
                        currentDateFajrTime
                    ),
                    nextAzan24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                        currentDateFajrTime
                    ),
                    sunrise12HrTime = hr12FormattedBanglaTimehhmmaaString(currentDateShuruqTime),
                    sunrise24HrTime = hr24FormattedTimeString(currentDateShuruqTime),
                    sunrise12HrTimeMilliSecond = TimeFormtter.getMillSecondFromTime(
                        currentDateShuruqTime
                    ),
                    sunrise24HrTimeMilliSecond =
                    TimeFormtter.getMilliSecondsFromHHmmss(
                        currentDateShuruqTime
                    ),
                    statusMessage = "",
                    isNotification = false,
                    dayTypeName = context.getString(R.string.vor),
                    meridiemType = TimeFormtter.get12HrFormattedaaaStringFrom24HrFormattedString(
                        hr24FormattedTimeString(currentDateFajrTime)
                    )
                )
            )
            add(
                DueAzanModel(
                    context.getString(R.string.dhor_ar_okto),
                    currentStartedAzan12HrTime = "",
                    currentStartedAzan12HrTimeMilliSecond = 0,
                    currentStartedAzan24HrTime = "",
                    currentStartedAzan24HrTimeMilliSecond = 0,
                    nextAzan12HrTime = hr12FormattedBanglaTimeString(currentDateDhuhrTime),
                    nextAzan24HrTime = hr24FormattedTimeString(currentDateDhuhrTime),
                    nextAzan12HrTimeMilliSecond =
                    TimeFormtter.get12HrFormattedyyyyMMddhhmmssaaDateTimeFrom24HrFormattedTimeMilli(
                        currentDateTimeMilli,
                        currentDateDhuhrTime
                    ),
                    nextAzan24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                        currentDateDhuhrTime
                    ),
                    sunrise12HrTime = hr12FormattedBanglaTimehhmmaaString(currentDateShuruqTime),
                    sunrise24HrTime = hr24FormattedTimeString(currentDateShuruqTime),
                    sunrise12HrTimeMilliSecond = TimeFormtter.getMillSecondFromTime(
                        currentDateShuruqTime
                    ),
                    sunrise24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                        currentDateShuruqTime
                    ),
                    statusMessage = "",
                    isNotification = false,
                    dayTypeName = context.getString(R.string.dupor),
                    meridiemType = TimeFormtter.get12HrFormattedaaaStringFrom24HrFormattedString(
                        hr24FormattedTimeString(currentDateDhuhrTime)
                    )
                )
            )
            add(
                DueAzanModel(
                    context.getString(R.string.asor_ar_okto),
                    currentStartedAzan12HrTime = "",
                    currentStartedAzan12HrTimeMilliSecond = 0,
                    currentStartedAzan24HrTime = "",
                    currentStartedAzan24HrTimeMilliSecond = 0,
                    nextAzan12HrTime = hr12FormattedBanglaTimeString(currentDateAsrTime),
                    nextAzan24HrTime = hr24FormattedTimeString(currentDateAsrTime),
                    nextAzan12HrTimeMilliSecond =
                    TimeFormtter.get12HrFormattedyyyyMMddhhmmssaaDateTimeFrom24HrFormattedTimeMilli(
                        currentDateTimeMilli,
                        currentDateAsrTime
                    ),
                    nextAzan24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                        currentDateAsrTime
                    ),
                    sunrise12HrTime = hr12FormattedBanglaTimehhmmaaString(currentDateShuruqTime),
                    sunrise24HrTime = hr24FormattedTimeString(currentDateShuruqTime),
                    sunrise12HrTimeMilliSecond = TimeFormtter.getMillSecondFromTime(
                        currentDateShuruqTime
                    ),
                    sunrise24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                        currentDateShuruqTime
                    ),
                    statusMessage = "",
                    isNotification = false,
                    dayTypeName = context.getString(R.string.bikal),
                    meridiemType = TimeFormtter.get12HrFormattedaaaStringFrom24HrFormattedString(
                        hr24FormattedTimeString(currentDateAsrTime)
                    )
                )
            )
            add(
                DueAzanModel(
                    context.getString(R.string.magrib_ar_okto),
                    currentStartedAzan12HrTime = "",
                    currentStartedAzan12HrTimeMilliSecond = 0,
                    currentStartedAzan24HrTime = "",
                    currentStartedAzan24HrTimeMilliSecond = 0,
                    nextAzan12HrTime = hr12FormattedBanglaTimeString(currentDateMaghribTime),
                    nextAzan24HrTime = hr24FormattedTimeString(currentDateMaghribTime),
                    nextAzan12HrTimeMilliSecond =
                    TimeFormtter.get12HrFormattedyyyyMMddhhmmssaaDateTimeFrom24HrFormattedTimeMilli(
                        currentDateTimeMilli,
                        currentDateMaghribTime
                    ),
                    nextAzan24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                        currentDateMaghribTime
                    ),
                    sunrise12HrTime = hr12FormattedBanglaTimehhmmaaString(currentDateShuruqTime),
                    sunrise24HrTime = hr24FormattedTimeString(currentDateShuruqTime),
                    sunrise12HrTimeMilliSecond = TimeFormtter.getMillSecondFromTime(
                        currentDateShuruqTime
                    ),
                    sunrise24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                        currentDateShuruqTime
                    ),
                    statusMessage = "",
                    isNotification = false,
                    dayTypeName = context.getString(R.string.sondha),
                    meridiemType = TimeFormtter.get12HrFormattedaaaStringFrom24HrFormattedString(
                        hr24FormattedTimeString(currentDateMaghribTime)
                    )
                )
            )
            add(
                DueAzanModel(
                    context.getString(R.string.isha_ar_okto),
                    currentStartedAzan12HrTime = "",
                    currentStartedAzan12HrTimeMilliSecond = 0,
                    currentStartedAzan24HrTime = "",
                    currentStartedAzan24HrTimeMilliSecond = 0,
                    nextAzan12HrTime = hr12FormattedBanglaTimeString(currentDateIshaaTime),
                    nextAzan24HrTime = hr24FormattedTimeString(currentDateIshaaTime),
                    nextAzan12HrTimeMilliSecond =
                    TimeFormtter.get12HrFormattedyyyyMMddhhmmssaaDateTimeFrom24HrFormattedTimeMilli(
                        currentDateTimeMilli,
                        currentDateIshaaTime
                    ),
                    nextAzan24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                        currentDateIshaaTime
                    ),
                    sunrise12HrTime = hr12FormattedBanglaTimehhmmaaString(currentDateShuruqTime),
                    sunrise24HrTime = hr24FormattedTimeString(currentDateShuruqTime),
                    sunrise12HrTimeMilliSecond = TimeFormtter.getMillSecondFromTime(
                        currentDateShuruqTime
                    ),
                    sunrise24HrTimeMilliSecond = TimeFormtter.getMilliSecondsFromHHmmss(
                        currentDateShuruqTime
                    ),
                    statusMessage = "",
                    isNotification = false,
                    dayTypeName = context.getString(R.string.rat),
                    meridiemType = TimeFormtter.get12HrFormattedaaaStringFrom24HrFormattedString(
                        hr24FormattedTimeString(currentDateIshaaTime)
                    )
                )
            )
        }
    }

    private fun hr12FormattedBanglaTimeString(time: Time) = LanguageConverter.getDateInBangla(
        TimeFormtter.get12HrFormattedhhStringFrom24HrFormattedString(hr24FormattedTimeString(time))
    ) + ":" + LanguageConverter.getDateInBangla(
        time.minute.toString()
    )

    private fun get24HrTimeTo12HrFormatV2(time: Time): Long {
        return TimeFormtter.milliSecondsFromTimeString(
            hr24FormattedTimeString(time)
        )
    }
/*    private fun hr12FormattedBanglaTimeString(time: Time) =
        time.hour.in12HrFormat().toString() + ":" + time.minute.toString()*/


    private fun hr12Formatted_aa_String(time: Time) =
        TimeFormtter.get12HrFormattedaaaStringFrom24HrFormattedString(
            hr24FormattedTimeString(time)
        )

    private fun hr12FormattedBanglaTimehhmmaaString(time: Time) =
        LanguageConverter.getDateInBangla(
            time.hour.in12HrFormat().toString()
        ) + ":" + LanguageConverter.getDateInBangla(
            time.minute.toString()
        ) + " " + hr12Formatted_aa_String(time)

    private fun hr12FormattedTimeString(time: Time): String {
        return TimeFormtter.get12HrFormattedhhmmStringFrom24HrFormattedString(time.hour.toString() + ":" + time.minute.toString() + ":" + time.second.toString())
    }

    private fun hr24FormattedTimeString(time: Time): String {
        return time.hour.toString() + ":" + time.minute.toString() + ":" + time.second.toString()
    }

    private fun getCurrentTimeMillihhmmssaa(): Long {
        return TimeFormtter.milliSecondsFromTimeString(
            TimeFormtter.getDateStringFromMilliSecondsIn12HrFormat(
                TimeFormtter.getCurrentTimeMilliFromDate()
            )
        )
    }
}