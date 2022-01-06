package ru.nikitaartamonov.materialdesign.data.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("planetary/apod")
    fun getDailyImage(@Query("api_key") key: String): Call<ImageWrapper>

    @GET("EPIC/api/natural/images")
    fun loadEarthPhotos(@Query("api_key") key: String): Call<List<EarthPhotoWrapper>>

    @GET("DONKI/GST")
    fun loadGstInfo(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("api_key") key: String
    ): Call<List<GstWrapper>>
}