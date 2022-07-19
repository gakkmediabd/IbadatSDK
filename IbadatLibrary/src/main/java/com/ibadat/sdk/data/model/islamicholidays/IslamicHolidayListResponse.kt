package com.ibadat.sdk.data.model.islamicholidays

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class IslamicHolidayListResponse {
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("totalRecords")
    @Expose
    var totalRecords: Int? = null

    @SerializedName("data")
    @Expose
    var data: List<Data>? = null

    @SerializedName("error")
    @Expose
    var error: Any? = null
    class Data{
        @SerializedName("about")
        @Expose
        val about: String? = null

        @SerializedName("category")
        val category: String? = null

        @SerializedName("categoryName")
        val categoryName: String? = null

        @SerializedName("contentBaseUrl")
        val contentBaseUrl: String? = null

        @SerializedName("createdBy")
        val createdBy: String? = null

        @SerializedName("createdOn")
        val createdOn: String? = null



        @SerializedName("id")
        val id: String? = null

        @SerializedName("imageUrl")
        val imageUrl: String? = null

        @SerializedName("isActive")
        val isActive: Boolean? = null

        @SerializedName("language")
        val language: String? = null

        @SerializedName("order")
        val order: Int? = null

        @SerializedName("pronunciation")
        val pronunciation: String? = null

        @SerializedName("refUrl")
        val refUrl: String? = null

        @SerializedName("subcategory")
        val subcategory: String? = null

        @SerializedName("subcategoryName")
        val subcategoryName: String? = null

        @SerializedName("text")
        val text: String? = null



        @SerializedName("title")
        val title: String? = null

        @SerializedName("updatedBy")
        val updatedBy: String? = null

        @SerializedName("updatedOn")
        val updatedOn: String? = null


    }
 }