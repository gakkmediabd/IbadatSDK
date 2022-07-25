package com.ibadat.sdk.data.model.nearby

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.ibadat.sdk.data.model.nearby.Viewport

class Geometry {
    @SerializedName("location")
    @Expose
    var location: Location? = null

    @SerializedName("viewport")
    @Expose
    var viewport: Viewport? = null
}