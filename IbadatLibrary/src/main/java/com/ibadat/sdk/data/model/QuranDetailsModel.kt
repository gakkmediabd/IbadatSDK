package com.ibadat.sdk.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class QuranDetailsModel {

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
        var data: List<QuranDetailsModel.Data>? = null

        @SerializedName("error")
        @Expose
        var error: Any? = null

        class Data {
            @SerializedName("surahId")
            @Expose
            var surahId: String? = null

            @SerializedName("text")
            @Expose
            var text: String? = null

            @SerializedName("textInArabic")
            @Expose
            var textInArabic: String? = null

            @SerializedName("pronunciation")
            @Expose
            var pronunciation: Any? = null

            @SerializedName("imageUrl")
            @Expose
            var imageUrl: Any? = null

            @SerializedName("contentUrl")
            @Expose
            var contentUrl: Any? = null

            @SerializedName("surah")
            @Expose
            var surah: Any? = null

            @SerializedName("contentBaseUrl")
            @Expose
            var contentBaseUrl: String? = null

            @SerializedName("id")
            @Expose
            var id: String? = null

            @SerializedName("createdBy")
            @Expose
            var createdBy: String? = null

            @SerializedName("createdOn")
            @Expose
            var createdOn: String? = null

            @SerializedName("updatedBy")
            @Expose
            var updatedBy: Any? = null

            @SerializedName("updatedOn")
            @Expose
            var updatedOn: Any? = null

            @SerializedName("isActive")
            @Expose
            var isActive: Boolean? = null

            @SerializedName("language")
            @Expose
            var language: String? = null

            @SerializedName("order")
            @Expose
            var order: Int? = null

            @SerializedName("about")
            @Expose
            var about: Any? = null
//            var ayahCountWithPrefix: String
//                get() {
//                    return "${BaseApplication.getAppContext().resources.getString(R.string.ayat)} ${
//                        LanguageConverter.getdateInBangla(
//
//                        )
//                    }"
//                }
//                set(value) {
//                    ayahCountWithPrefix = value
//                }
//            var durationLocalised: String
//                get() {
//                    return duration?.getLocalisedDuration() ?: "0"
//                }
//                set(value) {
//                    durationLocalised = value
//                }

        }
    }
