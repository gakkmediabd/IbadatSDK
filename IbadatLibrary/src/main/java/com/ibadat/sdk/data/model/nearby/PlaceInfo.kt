package com.ibadat.sdk.data.model.nearby

import com.google.gson.annotations.SerializedName
import com.ibadat.sdk.data.model.UserLocation


class PlaceInfo {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("address")
    var address: String? = null

    @SerializedName("location")
    var location: UserLocation? = null

    @SerializedName("placeLocation")
    var placeLocation: Location? = null
}