package com.ibadat.sdk.data.model.app_model

class DueAzanModel(
    var nextAzanName: String,
    var currentStartedAzan12HrTime: String,
    var currentStartedAzan12HrTimeMilliSecond: Long,
    var currentStartedAzan24HrTime: String,
    var currentStartedAzan24HrTimeMilliSecond: Long,
    var nextAzan12HrTime: String,
    var nextAzan24HrTime: String,
    var nextAzan12HrTimeMilliSecond: Long,
    var nextAzan24HrTimeMilliSecond: Long,
    var sunrise12HrTime: String,
    var sunrise24HrTime: String,
    var sunrise12HrTimeMilliSecond: Long,
    var sunrise24HrTimeMilliSecond: Long,
    var statusMessage: String,
    val isNotification: Boolean,
    val dayTypeName: String,
    val meridiemType: String
) {
    override fun toString(): String {
        return "nextAzanName='$nextAzanName', nextAzan12HrTime='$nextAzan12HrTime', nextAzan12HrTimeMilliSecond=$nextAzan12HrTimeMilliSecond"
    }
}