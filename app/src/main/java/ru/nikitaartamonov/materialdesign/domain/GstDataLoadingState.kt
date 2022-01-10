package ru.nikitaartamonov.materialdesign.domain

import ru.nikitaartamonov.materialdesign.data.retrofit.GstWrapper

sealed class GstDataLoadingState {

    data class Error(val error: Throwable) : GstDataLoadingState()
    data class Success(val gstData: List<GstWrapper>) : GstDataLoadingState()
}