package com.ibadat.sdk.data.model.nearby

import com.google.gson.annotations.SerializedName

data class NearbyResponse(
    @SerializedName("html_attributions") val htmlAttributions: List<Any>? = null,
    @SerializedName("results") val nearPlaceResults: List<NearPlaceResult>? = null,
    @SerializedName("status") val status: String
)
