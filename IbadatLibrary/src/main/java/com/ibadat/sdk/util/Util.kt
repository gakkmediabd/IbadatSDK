package com.ibadat.sdk.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

internal object Util {

    fun getUriFromPath(context: Context, fileNameExtension: String): Uri {
        return Uri.parse(context.filesDir.absolutePath + DIRECTORY_DATA + fileNameExtension)
    }

    fun getBitmapFromPath(context: Context, fileNameExtension: String): Bitmap {
        return BitmapFactory.decodeFile(getUriFromPath(context, fileNameExtension).path)
    }
}