package com.ibadat.sdk.util

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibadat.sdk.data.model.CommonDuaAndHadithModel
import com.ibadat.sdk.data.model.Data
import com.ibadat.sdk.data.model.SurahModel
import com.ibadat.sdk.roza.TimeFormtter
import java.lang.reflect.Type
import java.util.*

internal object AppConstantUtils {
    //Dua Model Bundle Constant
    var duaModel = ""
    //Dua Model Bundle Constant

    const val assets = "assets/"
    const val drawable_hdpi = "drawable-hdpi/"
    const val raw = "raw/"

    const val requestType = "RequestType"
    var requestTypeValue = 0
    var parameterPass = ""
    var parameterPassIndex = 0

    fun getJsonString(anyObject: Any): String {
        return Gson().toJson(anyObject)
    }

    fun getAnyObjectFromJsonString(strData: String, objectRef: Type): Any {
        return Gson().fromJson(strData, objectRef)
    }

    private var mLastClickTime: Long = 0

    fun getMLastClickTime() = mLastClickTime

    fun setMLastClickTime(clickTime: Long) {
        mLastClickTime = clickTime
    }

    fun checkCurrentAndNextAzanTime(context: Context, longTimeStamp: Long): Boolean {
        return if (longTimeStamp >= TimeFormtter.getCurrentTimeWithMillisecond()
        ) {
            true
        } else {
            Toast.makeText(
                context,
                "You can't set an alarm on an older date",
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }
}