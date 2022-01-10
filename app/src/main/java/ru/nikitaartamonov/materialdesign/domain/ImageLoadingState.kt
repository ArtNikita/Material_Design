package ru.nikitaartamonov.materialdesign.domain

import ru.nikitaartamonov.materialdesign.data.retrofit.ImageWrapper

sealed class ImageLoadingState {

    data class Error(val error: Throwable) : ImageLoadingState()
    data class Success(val imageWrapper: ImageWrapper) : ImageLoadingState()
}