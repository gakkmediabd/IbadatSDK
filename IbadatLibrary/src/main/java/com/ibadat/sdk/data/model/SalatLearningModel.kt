package com.ibadat.sdk.data.model

import com.google.gson.annotations.SerializedName

class SalatLearningModel {
    @SerializedName("Id")
    private var Id: String? = null

    @SerializedName("TopicName")
    private var TopicName: String? = null

    @SerializedName("TopicDescription")
    private var TopicDescription: String? = null

    @SerializedName("IsFavorite")
    private var IsFavorite: String? = null

    fun getIsFavorite(): String? {
        return IsFavorite
    }

    fun setIsFavorite(isFavorite: String?) {
        IsFavorite = isFavorite
    }

    fun getId(): String? {
        return Id
    }

    fun setId(id: String?) {
        Id = id
    }

    fun getTopicName(): String? {
        return TopicName
    }

    fun setTopicName(topicName: String?) {
        TopicName = topicName
    }

    fun getTopicDescription(): String? {
        return TopicDescription
    }

    fun setTopicDescription(topicDescription: String?) {
        TopicDescription = topicDescription
    }
}