package com.ibadat.sdk.data.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.MarkerOptions

internal class NearbySharedViewModel : ViewModel() {
    private val _markerOptions: MutableLiveData<Array<MarkerOptions>> = MutableLiveData()
    val markerOptions = _markerOptions

    fun shareMarkerOptions(markerOptions: Array<MarkerOptions>?) {
        markerOptions?.let { _markerOptions.value = it }
    }
}