package com.ibadat.sdk.data.restrepo

import com.ibadat.sdk.data.model.UserLocation
import com.ibadat.sdk.data.model.nearby.NearbyResponse

class RestRepo(
    private val nearbyApiService: ApiService
) {
    suspend fun getNearbyPlaceOfGivenType(
        key: String,
        radius: String,
        location: UserLocation,
        placeType: String,
        language: String
    ): NearbyResponse {
        val resultList = nearbyApiService.getNearbyPlace(
            key,
            radius,
            location.lat.toString() + "," + location.lng.toString(),
            placeType, language
        )
        return resultList
    }
}