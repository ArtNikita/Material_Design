package ru.nikitaartamonov.materialdesign.data.retrofit

import com.google.gson.annotations.SerializedName

data class EarthPhotoWrapper(
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("caption")
    val caption: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("date")
    val date: String
)