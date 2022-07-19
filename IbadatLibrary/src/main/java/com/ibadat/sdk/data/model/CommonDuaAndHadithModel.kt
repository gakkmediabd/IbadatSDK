package com.ibadat.sdk.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CommonDuaAndHadithModel() : Parcelable {
    @SerializedName("Id")
    @Expose
    private var id: String = String()

    @SerializedName("Title")
    @Expose
    private var title: String = String()

    @SerializedName("Dua")
    @Expose
    private var dua: String = String()

    @SerializedName("Pronounciation")
    @Expose
    private var pronounciation: String = String()

    @SerializedName("Meaning")
    @Expose
    private var meaning: String = String()

    @SerializedName("Fazilat")
    @Expose
    private var fazilat: String = String()

    @SerializedName("Narrator")
    @Expose
    private var narrator: String = String()

    @SerializedName("Description")
    @Expose
    private var description: String = String()

    @SerializedName("Source")
    @Expose
    private var source: String = String()

    @SerializedName("CreatedBy")
    @Expose
    private var createdBy: Any? = null

    @SerializedName("Sort")
    @Expose
    private var sort: Int = 0

    @SerializedName("Expire")
    @Expose
    private var expire: String = String()

    @SerializedName("Serial")
    @Expose
    private var serial: String = String()

    @SerializedName("IsFavorite")
    @Expose
    private var isFavorite: String = String()

    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        title = parcel.readString().toString()
        dua = parcel.readString().toString()
        pronounciation = parcel.readString().toString()
        meaning = parcel.readString().toString()
        fazilat = parcel.readString().toString()
        narrator = parcel.readString().toString()
        description = parcel.readString().toString()
        source = parcel.readString().toString()
        sort = parcel.readInt()
        expire = parcel.readString().toString()
        serial = parcel.readString().toString()
        isFavorite = parcel.readString().toString()
    }

    fun getId(): String {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getDua(): String {
        return dua
    }

    fun setDua(dua: String) {
        this.dua = dua
    }

    fun getPronounciation(): String {
        return pronounciation
    }

    fun setPronounciation(pronounciation: String) {
        this.pronounciation = pronounciation
    }

    fun getMeaning(): String {
        return meaning
    }

    fun setMeaning(meaning: String) {
        this.meaning = meaning
    }

    fun getFazilat(): String {
        return fazilat
    }

    fun setFazilat(fazilat: String) {
        this.fazilat = fazilat
    }

    fun getNarrator(): String {
        return narrator
    }

    fun setNarrator(narrator: String) {
        this.narrator = narrator
    }

    fun getDescription(): String {
        return description
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun getSource(): String {
        return source
    }

    fun setSource(source: String) {
        this.source = source
    }

    fun getCreatedBy(): Any? {
        return createdBy
    }

    fun setCreatedBy(createdBy: Any?) {
        this.createdBy = createdBy
    }

    fun getSort(): Int {
        return sort
    }

    fun setSort(sort: Int) {
        this.sort = sort
    }

    fun getExpire(): String {
        return expire
    }

    fun setExpire(expire: String) {
        this.expire = expire
    }

    fun getSerial(): String {
        return serial
    }

    fun setSerial(serial: String) {
        this.serial = serial
    }

    fun getIsFavorite(): String {
        return isFavorite
    }

    fun setIsFavorite(isFavorite: String) {
        this.isFavorite = isFavorite
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(dua)
        parcel.writeString(pronounciation)
        parcel.writeString(meaning)
        parcel.writeString(fazilat)
        parcel.writeString(narrator)
        parcel.writeString(description)
        parcel.writeString(source)
        parcel.writeInt(sort)
        parcel.writeString(expire)
        parcel.writeString(serial)
        parcel.writeString(isFavorite)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommonDuaAndHadithModel> {
        override fun createFromParcel(parcel: Parcel): CommonDuaAndHadithModel {
            return CommonDuaAndHadithModel(parcel)
        }

        override fun newArray(size: Int): Array<CommonDuaAndHadithModel?> {
            return arrayOfNulls(size)
        }
    }
}