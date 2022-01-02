package ru.nikitaartamonov.materialdesign.domain

interface ImageLoader {

    fun loadImage(callback: (ImageLoadingState) -> Unit)
}