package ru.nikitaartamonov.materialdesign.domain

import ru.nikitaartamonov.materialdesign.data.retrofit.EarthPhotoWrapper

sealed class EarthPhotosLoadingState {

    data class Error(val error: Throwable) : EarthPhotosLoadingState()
    data class Success(val earthImages: List<EarthPhotoWrapper>) : EarthPhotosLoadingState()
}