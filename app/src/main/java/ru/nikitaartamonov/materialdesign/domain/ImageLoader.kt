package ru.nikitaartamonov.materialdesign.domain

import ru.nikitaartamonov.materialdesign.data.retrofit.ImageWrapper

interface ImageLoader {

    fun loadImage(callback: (State) -> Unit)

    sealed class State {

        data class Error(val error: Throwable) : State()
        data class Success(val imageWrapper: ImageWrapper) : State()
    }
}