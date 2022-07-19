package com.ibadat.sdk.util

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.text.TextUtils
import android.util.Base64
import android.view.View
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

fun View.handleClickEvent(clickAction: () -> Unit) {

    this.setOnClickListener {
        // mis-clicking prevention, using threshold of 1000 ms
        if (((SystemClock.elapsedRealtime() - AppConstantUtils.getMLastClickTime()) < 300).not()) {
            AppConstantUtils.setMLastClickTime(SystemClock.elapsedRealtime())
            clickAction()
        }
    }
}
fun Activity.setStatusColor(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        window.statusBarColor = ContextCompat.getColor(applicationContext, color)
    }
}

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(
        this.context,
        DividerItemDecoration.VERTICAL
    )
    val drawable = ContextCompat.getDrawable(
        this.context,
        drawableRes
    )
    drawable?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}



fun <T> List<T>.mutableCopyOf(): MutableList<T> {
    val original = this
    return mutableListOf<T>().apply { addAll(original) }
}


fun View.resizeView(viewDimen: ViewDimension, screenWdth: Int?, context: Context) {
    when (viewDimen) {
        ViewDimension.HalfScreenWidth -> if (screenWdth != null) {
            this.layoutParams.width =
                Math.ceil((screenWdth / 2 - 20 * context.resources.displayMetrics.density).toDouble())
                    .toInt()
        }
        ViewDimension.HalfScreenWidthMargin -> if (screenWdth != null) {
            this.layoutParams.width =
                Math.ceil((screenWdth / 2 - 28 * context.resources.displayMetrics.density).toDouble())
                    .toInt()
        }
        ViewDimension.OneFourthScreenWidth -> if (screenWdth != null) {
            this.layoutParams?.width =
                Math.ceil((screenWdth / 4 - 10 * context.resources.displayMetrics.density).toDouble())
                    .toInt()
        }
    }

}



fun Int.in12HrFormat(): Int {
    when {
        this == 12 -> return 0
        this > 12 -> return this - 12
        else -> return this
    }
}
fun isNetworkConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (cm != null) {
        cm.activeNetworkInfo != null
    } else {
        false
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun String.getTime(): String {
    var finalDate = this
    val dateFormatCurrent = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val objDate: Date? = dateFormatCurrent.parse(this)

    objDate?.let {
        val dateFormatRequired = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
        finalDate = dateFormatRequired.format(it)
    }

    return finalDate
}

fun Context.copyToClipboard(text: CharSequence) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
}

sealed class ViewDimension {
    object OneFourthScreenWidth : ViewDimension()
    object HalfScreenWidth : ViewDimension()
    object HalfScreenWidthMargin : ViewDimension()
}

