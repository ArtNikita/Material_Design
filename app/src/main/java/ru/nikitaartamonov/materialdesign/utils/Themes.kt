package ru.nikitaartamonov.materialdesign.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import ru.nikitaartamonov.materialdesign.R

enum class Themes(val styleId: Int) {

    POLICE(R.style.Theme_Police),
    PURPLE_AND_YELLOW(R.style.Theme_PurpleAndYellow);

    companion object {
        private const val THEME_KEY = "THEME_KEY"
        private val DEFAULT_THEME = POLICE
        private const val THEMES_PREF_NAME = "THEMES_PREF_NAME"

        fun getCurrentTheme(context: Context): Int {
            val sharedPreferences = context.getSharedPreferences(
                THEMES_PREF_NAME,
                Application.MODE_PRIVATE
            )
            val currentTheme: String =
                sharedPreferences.getString(
                    THEME_KEY,
                    DEFAULT_THEME.toString()
                ) ?: DEFAULT_THEME.toString()
            return valueOf(currentTheme).styleId
        }

        fun setTheme(theme: Themes, currentActivity: Activity) {
            val sharedPreferences = currentActivity.getSharedPreferences(
                THEMES_PREF_NAME,
                Application.MODE_PRIVATE
            )
            val currentTheme =
                sharedPreferences.getString(THEME_KEY, DEFAULT_THEME.toString())
            if (currentTheme == theme.toString()) return
            sharedPreferences.edit().let { editor ->
                editor.putString(THEME_KEY, theme.toString())
                editor.commit()
            }
            currentActivity.recreate()
        }
    }
}
