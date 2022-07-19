package com.ibadat.sdk.util

import android.content.Context
import android.location.Geocoder
import com.ibadat.sdk.GPSTrac.GpsTracker
import com.ibadat.sdk.R
import com.ibadat.sdk.data.manager.prefs.AppPreference
import java.io.IOException
import java.util.*

internal object LocationFinder {
    private var latitude = 0.0
    private var longitude = 0.0
    private var localityName = ""
    private var cityName = ""
    var finalLocation = ""

    fun getLocationName(context: Context): String {
        val gpsTracker = GpsTracker(context)
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.latitude
            longitude = gpsTracker.longitude

            AppPreference.lastLatitude = latitude.toString()
            AppPreference.lastLongitude = longitude.toString()
            val geocoder = Geocoder(context, Locale.getDefault())
            try {
                val addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    10
                )
                if (addresses.size != 0) {
                    val address = addresses[0]
                    localityName = address.countryName
                    cityName =
                        if (address.locality == null) address.adminArea else address.locality
                    if (localityName != "" && cityName != "") {
                        GlobalVar.CITY_NAME =
                            cityName
                        localityName =
                            localityName.uppercase()
                        cityName =
                            cityName.uppercase()
                        finalLocation = "$cityName, $localityName"

                    } else if (localityName != "") {
                        finalLocation = localityName.uppercase()
                    } else if (cityName != "") {
                        finalLocation = cityName.uppercase()
                    } else {
                        finalLocation = context.getString(R.string.text_not_found)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            AppPreference.lastLocationName = finalLocation
        }
        return finalLocation
    }
}