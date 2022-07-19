package com.ibadat.sdk.data.model.app_model

internal class PrayerTimeModel(
    val indexNumber: Int,
    val azanName: String,
    val isNotification: Boolean,
    val dayTypeName: String,
    val azanTime: String,
    val meridiemType: String


) {
    override fun toString(): String {
        return "PrayerTimeModel(azanName='$azanName', azanTime='$azanTime')"
    }
}