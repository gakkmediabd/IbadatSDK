package com.ibadat.sdk.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.ibadat.sdk.util.NetworkCheck
import android.widget.Toast

object NetworkCheck {
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun showNetWorkToast(context: Context): Boolean {
        return if (isNetworkAvailable(context)) {
            true
        } else {
            Toast.makeText(context, "No Internet Connection !!", Toast.LENGTH_SHORT).show()
            false
        }
    }
}