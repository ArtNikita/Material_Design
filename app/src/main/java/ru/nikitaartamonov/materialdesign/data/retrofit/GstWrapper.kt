package ru.nikitaartamonov.materialdesign.data.retrofit

import com.google.gson.annotations.SerializedName

data class GstWrapper(
    @SerializedName("startTime")
    val startTime: String
)