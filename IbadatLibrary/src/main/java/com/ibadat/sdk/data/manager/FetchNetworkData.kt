package com.ibadat.sdk.data.manager

import android.util.Base64
import com.ibadat.sdk.BuildConfig
import com.ibadat.sdk.data.manager.prefs.AppPreference
import com.ibadat.sdk.data.model.CommonDuaAndHadithModel
import com.ibadat.sdk.data.model.nearby.NearbyResponse
import com.ibadat.sdk.data.restrepo.ApiService
import com.ibadat.sdk.data.restrepo.NetworkDataCallBack
import com.ibadat.sdk.data.restrepo.RetroClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal object FetchNetworkData {
    private val apiService: ApiService = RetroClient.getDuaApiService()
    private val apiServiceNearMosque: ApiService = RetroClient.getNearestMosqueApiService()

    private fun getBasicAuth(): String {
        val username = "Gakkmedia"
        val password = "GaramD@nC0k@"
        val customerUsernamePassword = "$username:$password"
        return "Basic " + Base64.encodeToString(
            customerUsernamePassword
                .toByteArray(),
            Base64.NO_WRAP
        )
    }

    private fun getHasMapIDAndLang(msisdn: String, lang: String): HashMap<String, Any> {
        val queryData = HashMap<String, Any>()
        queryData["msisdn"] = "8801783355888"
        queryData["lang"] = "bn"
        return queryData
    }

    private fun getHasMapNearMosque(radius: String): HashMap<String, Any> {
        val userLocation = AppPreference.getUserCurrentLocation();
        val queryData = HashMap<String, Any>()
        queryData["key"] = BuildConfig.MAP_API_KEY
        queryData["radius"] = radius
        queryData["location"] = userLocation.lat.toString() + "," + userLocation.lng.toString()
        queryData["type"] = "mosque"
        queryData["language"] = "bn"
        return queryData
    }

    fun fetchDua(networkDataCallBack: NetworkDataCallBack) {
        apiService.getAllDua(getBasicAuth(), getHasMapIDAndLang("", ""))
            .enqueue(object : Callback<MutableList<CommonDuaAndHadithModel>> {
                override fun onResponse(
                    call: Call<MutableList<CommonDuaAndHadithModel>>,
                    response: Response<MutableList<CommonDuaAndHadithModel>>
                ) {
                    if (response.isSuccessful) {
                        networkDataCallBack.onLoading(response.isSuccessful)
                        networkDataCallBack.onSuccess(response.body()!!)
                    } else {
                        networkDataCallBack.onLoading(response.isSuccessful)
                        networkDataCallBack.onSuccess(mutableListOf<CommonDuaAndHadithModel>())
                    }
                }

                override fun onFailure(
                    call: Call<MutableList<CommonDuaAndHadithModel>>,
                    throwable: Throwable
                ) {
                    networkDataCallBack.onLoading(false)
                    networkDataCallBack.onError(throwable.message!!)
                }
            })
    }

    fun fetchHadith(networkDataCallBack: NetworkDataCallBack) {
        apiService.getAllHadith(getBasicAuth(), getHasMapIDAndLang("", ""))
            .enqueue(object : Callback<MutableList<CommonDuaAndHadithModel>> {
                override fun onResponse(
                    call: Call<MutableList<CommonDuaAndHadithModel>>,
                    response: Response<MutableList<CommonDuaAndHadithModel>>
                ) {
                    if (response.isSuccessful) {
                        networkDataCallBack.onLoading(response.isSuccessful)
                        networkDataCallBack.onSuccess(response.body()!!)
                    } else {
                        networkDataCallBack.onLoading(response.isSuccessful)
                        networkDataCallBack.onSuccess(mutableListOf<CommonDuaAndHadithModel>())
                    }
                }

                override fun onFailure(
                    call: Call<MutableList<CommonDuaAndHadithModel>>,
                    throwable: Throwable
                ) {
                    networkDataCallBack.onLoading(false)
                    networkDataCallBack.onError(throwable.message!!)
                }
            })
    }

    fun fetchNearbyPlace(radius: String, networkDataCallBack: NetworkDataCallBack) {
        apiServiceNearMosque.getNearbyPlace(getHasMapNearMosque(radius))
            .enqueue(object : Callback<NearbyResponse> {
                override fun onResponse(
                    call: Call<NearbyResponse>,
                    response: Response<NearbyResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            networkDataCallBack.onLoading(response.isSuccessful)
                            networkDataCallBack.onSuccess(response.body()!!.nearPlaceResults)
                        }
                    } else {
                        networkDataCallBack.onLoading(response.isSuccessful)
                        networkDataCallBack.onSuccess(mutableListOf<NearbyResponse>())
                    }
                }

                override fun onFailure(call: Call<NearbyResponse>, throwable: Throwable) {
                    networkDataCallBack.onLoading(false)
                    networkDataCallBack.onError(throwable.message!!)
                }
            })
    }
}