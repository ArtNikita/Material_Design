package ru.nikitaartamonov.materialdesign.app

import android.app.Activity
import android.app.Application
import android.content.Context
import ru.nikitaartamonov.materialdesign.data.retrofit.NasaDataLoaderRetrofit
import ru.nikitaartamonov.materialdesign.domain.NasaDataLoader
import ru.nikitaartamonov.materialdesign.domain.Themes

class App : Application() {

    val nasaDataLoader: NasaDataLoader by lazy { NasaDataLoaderRetrofit() }

    private val sharedPreferences by lazy {
        baseContext.getSharedPreferences(Themes.THEMES_PREF_NAME, MODE_PRIVATE)
    }

    fun getCurrentTheme(): Int {
        val currentTheme: String =
            sharedPreferences.getString(
                Themes.THEME_KEY,
                Themes.DEFAULT_THEME.toString()
            ) ?: Themes.DEFAULT_THEME.toString()
        return Themes.Entities.valueOf(currentTheme).styleId
    }

    fun setTheme(theme: Themes.Entities, currentActivity: Activity) {
        val currentTheme =
            sharedPreferences.getString(Themes.THEME_KEY, Themes.DEFAULT_THEME.toString())
        if (currentTheme == theme.toString()) return
        sharedPreferences.edit().let { editor ->
            editor.putString(Themes.THEME_KEY, theme.toString())
            editor.commit()
        }
        currentActivity.recreate()
    }
}

fun Context.app(): App = applicationContext as App