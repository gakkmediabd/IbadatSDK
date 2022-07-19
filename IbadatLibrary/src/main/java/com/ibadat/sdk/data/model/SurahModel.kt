package com.ibadat.sdk.data.model

import com.google.gson.annotations.SerializedName

class SurahModel {

    @SerializedName("Id")
    private var Id: String? = null

    @SerializedName("Title")
    private var Title: String? = null

    @SerializedName("Narrator")
    private var Narrator: String? = null

    @SerializedName("Description")
    private var Description: String? = null

    @SerializedName("Source")
    private var Source: String? = null

    @SerializedName("Serial")
    private var Serial: String? = null

    @SerializedName("IsFavorite")
    private var IsFavorite: String? = null

    fun getIsFavorite(): String? {
        return IsFavorite
    }

    fun setIsFavorite(isFavorite: String?) {
        IsFavorite = isFavorite
    }

    fun getSerial(): String? {
        return Serial
    }

    fun setSerial(serial: String?) {
        Serial = serial
    }

    fun getId(): String? {
        return Id
    }

    fun setId(id: String?) {
        Id = id
    }

    fun getTitle(): String? {
        return Title
    }

    fun setTitle(title: String?) {
        Title = title
    }

    fun getNarrator(): String? {
        return Narrator
    }

    fun setNarrator(narrator: String?) {
        Narrator = narrator
    }

    fun getDescription(): String? {
        return Description
    }

    fun setDescription(description: String?) {
        Description = description
    }

    fun getSource(): String? {
        return Source
    }

    fun setSource(source: String?) {
        Source = source
    }
}