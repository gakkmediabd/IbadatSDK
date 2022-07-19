package com.ibadat.sdk.data.model.islamicholidays

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class IslamicHolidays(
    @SerializedName("about")
    val about: String? = null,



    @SerializedName("category")
    val category: String? = null,

    @SerializedName("categoryName")
    val categoryName: String? = null,

    @SerializedName("contentBaseUrl")
    val contentBaseUrl: String? = null,

    @SerializedName("createdBy")
    val createdBy: String? = null,

    @SerializedName("createdOn")
    val createdOn: String? = null,


    @SerializedName("id")
    val id: String? = null,

    @SerializedName("imageUrl")
    val imageUrl: String? = null,

    @SerializedName("isActive")
    val isActive: Boolean? = null,

    @SerializedName("language")
    val language: String? = null,

    @SerializedName("order")
    val order: Int,

    @SerializedName("pronunciation")
    val pronunciation: String? = null,

    @SerializedName("refUrl")
    val refUrl: String? = null,

    @SerializedName("subcategory")
    val subcategory: String? = null,

    @SerializedName("subcategoryName")
    val subcategoryName: String? = null,

    @SerializedName("text")
    val text: String? = null,



    @SerializedName("title")
    val title: String? = null,

    @SerializedName("updatedBy")
    val updatedBy: String? = null,

    @SerializedName("updatedOn")
    val updatedOn: String? = null,



) : Serializable {
    val fullImageUrl: String?
        get() = "$contentBaseUrl/$imageUrl"
}