package com.ibadat.sdk.data.restrepo

import com.ibadat.sdk.data.restrepo.RetroClient.Companion.getNearestMosqueApiService


class RetroClient2 {
    companion object {
        suspend fun getRepository(): RestRepo {
            return RestRepo(nearbyApiService = getNearestMosqueApiService()!!)
        }
    }
}