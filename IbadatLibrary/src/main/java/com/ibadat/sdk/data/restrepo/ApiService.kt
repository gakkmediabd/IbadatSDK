package com.ibadat.sdk.data.restrepo

import com.ibadat.sdk.data.model.*
import com.ibadat.sdk.data.model.Jakat.Jakat
import com.ibadat.sdk.data.model.islamicholidays.IslamicHolidayListResponse
import com.ibadat.sdk.data.model.nearby.NearbyResponse
import com.ibadat.sdk.data.model.WallpaperAnimModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("ayat/ibadat/bysurah/{id}-{lang}//")
    fun getSurahDetails(
        @Path("id") id: Int?,
        @Path("lang") lang: String?
    ): Call<QuranDetailsModel>

    @GET("dua")
    fun getAllDua(
        @Header("Authorization") basicAuth: String,
        @QueryMap queryData: HashMap<String, Any>
    ): Call<MutableList<CommonDuaAndHadithModel>>

    @GET("Hadith")
    fun getAllHadith(
        @Header("Authorization") basicAuth: String,
        @QueryMap queryData: HashMap<String, Any>
    ): Call<MutableList<CommonDuaAndHadithModel>>

    @GET("textcontent/bycategory/{catId}/{subCatId}/{pageNo}/30")
    fun getIslamicHolidays(
        @Path("catId") catId: String,
        @Path("subCatId") subCatId: String,
        @Path("pageNo") pageNo: String
    ): Call<IslamicHolidayListResponse>

    @GET("textcontent/bycategory/{catId}/{subCatId}/{pageNo}/30")
    fun getJakatList(
        @Path("catId") catId: String,
        @Path("subCatId") subCatId: String,
        @Path("pageNo") pageNo: String
    ): Call<Jakat>

    @GET("topic?")
    fun getAllINamazShikka(
        @Query("id") id: String,
        @Query("lang") lang: String
    ): Call<List<SalatLearningModel>>

    @GET("topic?")
    fun getAllINamazShikkaDescription(
        @Query("id") id: String?,
        @Query("type") type: String?,
        @Query("msisdn") msisdn: String?,
        @Query("lang") lang: String?
    ): Call<List<SalatLearningModel>>

    @GET("home?")
    fun getLiveVideo(
        @Query("id") id: String?,
        @Query("lang") lang: String?,
        @Query("waqt") waqt: String?
    ): Call<LiveVideo>

    @GET("json?sensor=true")
    fun getNearbyPlace(
        @QueryMap queryData: HashMap<String, Any>
    ): Call<NearbyResponse>

    @GET("api/Content?")
    fun getAllWallpaperAnimation(
        @Query("portalCode") portalCode: String?,
        @Query("categoryCode") categoryCode: String?,
        @Query("contentType") contentType: String?
    ): Call<List<WallpaperAnimModel?>?>?

}