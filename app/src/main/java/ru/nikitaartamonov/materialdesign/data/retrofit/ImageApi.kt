package ru.nikitaartamonov.materialdesign.data.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {
    @GET("planetary/apod")
    fun getImage(
        @Query("api_key") key: String
    ) : Call<ImageWrapper>
}