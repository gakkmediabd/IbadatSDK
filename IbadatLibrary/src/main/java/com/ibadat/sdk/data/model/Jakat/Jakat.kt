package com.ibadat.sdk.data.model.Jakat



data class Jakat(
    val `data`: List<DataJakat>,
    val error: Any,
    val message: String,
    val status: Int,
    val totalRecords: Int
)