package com.ibadat.sdk.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class WallpaperAnimModel : Serializable {
    @SerializedName("contentCode")
    var contentCode: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("previwUrl")
    var previwUrl: String? = null

    @SerializedName("ownerName")
    var ownerName: String? = null

    @SerializedName("typeName")
    var typeName: String? = null

    @SerializedName("physicalUrl")
    var physicalUrl: String? = null

    @SerializedName("contentType")
    var contentType: String? = null
}

