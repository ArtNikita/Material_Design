package ru.nikitaartamonov.materialdesign.domain

import ru.nikitaartamonov.materialdesign.data.retrofit.ImageWrapper

interface ImageLoader {
    fun loadImage(callback: (ImageWrapper?) -> Unit)
}