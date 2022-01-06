package ru.nikitaartamonov.materialdesign.app

import android.app.Application
import ru.nikitaartamonov.materialdesign.data.retrofit.ImageLoaderRetrofit
import ru.nikitaartamonov.materialdesign.domain.ImageLoader

class App : Application() {

    val imageLoader: ImageLoader by lazy { ImageLoaderRetrofit() }
}