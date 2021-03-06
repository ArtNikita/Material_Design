package ru.nikitaartamonov.materialdesign.app

import android.app.Application
import android.content.Context
import ru.nikitaartamonov.materialdesign.data.retrofit.NasaDataLoaderRetrofit
import ru.nikitaartamonov.materialdesign.domain.NasaDataLoader

class App : Application() {

    val nasaDataLoader: NasaDataLoader by lazy { NasaDataLoaderRetrofit() }
}

fun Context.app(): App = applicationContext as App