package com.ibadat.sdk.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 4/4/2018.
 */

public class HadithModel {


    @SerializedName("Id")
    public String Id;
    @SerializedName("Title")
    public String Title;
    @SerializedName("Narrator")
    public String Narrator;
    @SerializedName("Description")
    public String Description;
    @SerializedName("Source")
    public String Source;

    @SerializedName("Serial")
    public String Serial;

    @SerializedName("IsFavorite")
    public String IsFavorite;

    public String getIsFavorite() {
        return IsFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        IsFavorite = isFavorite;
    }

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String serial) {
        Serial = serial;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNarrator() {
        return Narrator;
    }

    public void setNarrator(String narrator) {
        Narrator = narrator;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }
}
