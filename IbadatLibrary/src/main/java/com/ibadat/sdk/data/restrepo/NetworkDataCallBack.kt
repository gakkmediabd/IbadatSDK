package com.ibadat.sdk.data.restrepo

interface NetworkDataCallBack {
    fun onLoading(loading: Boolean)
    fun onSuccess(response: Any)
    fun onError(errorMessage: String)
//    fun onStatusLoginOrLogout(statusMessage: Int)
}