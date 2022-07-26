package com.ibadat.sdk.data.restrepo

import com.ibadat.sdk.util.GlobalVar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class RetroClient {
    companion object {
        private fun getClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(
                    BasicAuthInterceptor(
                        "Gakkmedia",
                        "GaramD@nC0k@"
                    )
                )
                .build()
        }

        private fun getAuthorizedClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(
                    OAuthInterceptor(
                        "Bearer",
                        "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiI2MDE5Mzc3NjU3N2E3ZGYxM2VkYTRjNjciLCJVc2VySWQiOiI2MDE5Mzc3NjU3N2E3ZGYxM2VkYTRjNjciLCJVc2VyTmFtZSI6Ijg4MDE1Mzc2NzM5NzciLCJJbWFnZSI6IiIsInJvbGUiOiJ1c2VyIiwibmJmIjoxNjEyMjY1MzM1LCJleHAiOjE5Mjc2MjUzMzUsImlhdCI6MTYxMjI2NTMzNSwiaXNzIjoibG9sbGlwb3AiLCJhdWQiOiJsb2xsaXBvcCJ9.pA0fJa5dSCIJUuwmOY4RxQ0fSAQNhoZCHMaG94-ZkndOn9RxHqsEdq_uByTTQR3MCC2V_ajkcCBl7e4idNp5SA"
                    )
                )
                .build();
        }

        private fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(GlobalVar.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build()
        }

        private fun getRetrofitInstanceAuthorized(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(GlobalVar.ISLAMICHOLIDAYs_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getAuthorizedClient())
                .build()
        }

        /**
         * Get API Service
         * @return API Service
         */
        fun getApiService(): ApiService? {
            return getRetrofitInstance().create(ApiService::class.java)
        }

        private fun getSubsRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(GlobalVar.BASE_URL_SUBSCRIPTION)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build()
        }

        private fun getRetroInstanceOfDownloadInfo(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(GlobalVar.BASE_URL_DOWNLOAD_CHECK)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build()
        }

        fun getDownloadInfoInstance(): ApiService? {
            return getRetroInstanceOfDownloadInfo().create(ApiService::class.java)
        }

        fun getSubsApiService(): ApiService? {
            return getSubsRetrofitInstance().create(ApiService::class.java)
        }

        private fun getRetrofitForQuranApis(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(GlobalVar.QURAN_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun getRetrofitForSalatLearningApis(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(GlobalVar.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun getRetrofitForNearestMosqueApi(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(GlobalVar.BASE_URL_NEARBY_PLACE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun getRetrofitForDuaApis(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(GlobalVar.DUA_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getNamazApiService(): ApiService? {
            return getRetrofitForSalatLearningApis().create(ApiService::class.java)
        }

        fun getQuranApiService(): ApiService? {
            return getRetrofitForQuranApis().create(ApiService::class.java)
        }

        fun getIslamicHolidaysApiService(): ApiService? {
            return getRetrofitInstanceAuthorized().create(ApiService::class.java)
        }

        fun getJakatApiService(): ApiService? {
            return getRetrofitInstanceAuthorized().create(ApiService::class.java)
        }

        fun getLiveVideoApiService(): ApiService? {
            return getRetrofitForSalatLearningApis().create(ApiService::class.java)
        }

        fun getNearestMosqueApiService(): ApiService {
            return getRetrofitForNearestMosqueApi().create(ApiService::class.java)
        }

        fun getDuaApiService(): ApiService {
            return getRetrofitForDuaApis().create(ApiService::class.java)
        }

        private fun getRamadanTiming(): Retrofit {
            return Retrofit.Builder().baseUrl(GlobalVar.RAMADAN_TIMING_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getRamadanTimingApiService(): ApiService? {
            return getRamadanTiming().create(ApiService::class.java)
        }

        private fun getNearbyPlaces(): Retrofit {
            return Retrofit.Builder().baseUrl(GlobalVar.BASE_URL_NEARBY_PLACE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getNearbyPlacesApiService(): ApiService? {
            return getNearbyPlaces().create(ApiService::class.java)
        }

        private fun getCurrentWeather(): Retrofit {
            return Retrofit.Builder().baseUrl(GlobalVar.CURRENT_WEATHER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getCurrentWeatherApi(): ApiService? {
            return getCurrentWeather().create(ApiService::class.java)
        }

        //CURRENCY
        private fun getCurrencyInfo(): Retrofit {
            return Retrofit.Builder().baseUrl(GlobalVar.CURRENCY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getCurrencyApiService(): ApiService? {
            return getCurrencyInfo().create(ApiService::class.java)
        }

        private fun getRetrofitSubsApis(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(GlobalVar.SUBS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getGPSubsApiService(): ApiService? {
            return getRetrofitSubsApis().create(ApiService::class.java)
        }

        fun getGenericApiService(url: String): ApiService? {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }

    internal class OAuthInterceptor(
        private val tokenType: String,
        private val acceessToken: String?
    ) :
        Interceptor {

        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()
            request =
                request.newBuilder().header("Authorization", "$tokenType $acceessToken").build()
            return chain.proceed(request)
        }
    }
}