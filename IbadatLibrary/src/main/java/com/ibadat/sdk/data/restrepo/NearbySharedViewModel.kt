package com.ibadat.sdk.data.restrepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.MarkerOptions

internal class NearbySharedViewModel : ViewModel() {
    private val _range: MutableLiveData<Int> = MutableLiveData()
    val range: LiveData<Int> = _range


    private val _markerOptions: MutableLiveData<Array<MarkerOptions>> = MutableLiveData()
    public val markerOptions = _markerOptions
    fun setRange(km: Int) {
        _range.value = km
    }

    fun shareMarkerOptions(markerOptions: Array<MarkerOptions>?) {
        markerOptions?.let { _markerOptions.value = it }
    }
}