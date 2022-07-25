package com.ibadat.sdk.data.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.ibadat.sdk.data.model.nearby.Viewport

/**
 * Created by user on 4/4/2018.
 */
class HadithModel {
    @SerializedName("Id")
    var id: String? = null

    @SerializedName("Title")
    var title: String? = null

    @SerializedName("Narrator")
    var narrator: String? = null

    @SerializedName("Description")
    var description: String? = null

    @SerializedName("Source")
    var source: String? = null

    @SerializedName("Serial")
    var serial: String? = null

    @SerializedName("IsFavorite")
    var isFavorite: String? = null
}