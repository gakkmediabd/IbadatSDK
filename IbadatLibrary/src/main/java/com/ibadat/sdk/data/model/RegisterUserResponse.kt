package com.ibadat.sdk.data.model

import com.google.gson.annotations.SerializedName

data class RegisterUserResponse (

    @SerializedName("data")
    val `data`: Data?=null,

    @SerializedName("error")
    val error: String?=null,

    @SerializedName("message")
    val message: String?=null,

    @SerializedName("status")
    val status: Int?=null,

    @SerializedName("totalRecords")
    val totalRecords: Int?=null
)

data class Data(
    val roles: List<String>,
    val token: String,
    val userId: String
)