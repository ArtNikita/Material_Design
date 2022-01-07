package ru.nikitaartamonov.materialdesign.app

import android.app.Application
import android.content.Context
import ru.nikitaartamonov.materialdesign.data.retrofit.NasaDataLoaderRetrofit
import ru.nikitaartamonov.materialdesign.domain.NasaDataLoader
import ru.nikitaartamonov.materialdesign.domain.Themes

class App : Application() {

    val nasaDataLoader: NasaDataLoader by lazy { NasaDataLoaderRetrofit() }

    fun getCurrentTheme(): Int {
        val currentTheme: String =
            baseContext.getSharedPreferences(Themes.THEMES_PREF_NAME, MODE_PRIVATE).getString(
                Themes.THEME_KEY,
                Themes.DEFAULT_THEME.toString()
            ) ?: Themes.DEFAULT_THEME.toString()
        return Themes.Entities.valueOf(currentTheme).styleId
    }
}

fun Context.app(): App = applicationContext as App