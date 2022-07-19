package com.ibadat.sdk.data.manager

import android.util.Base64
import com.ibadat.sdk.data.model.CommonDuaAndHadithModel
import com.ibadat.sdk.data.restrepo.ApiService
import com.ibadat.sdk.data.restrepo.NetworkDataCallBack
import com.ibadat.sdk.data.restrepo.RetroClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal object FetchNetworkData {
    private val apiService: ApiService = RetroClient.getDuaApiService()

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
}