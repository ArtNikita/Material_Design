package ru.nikitaartamonov.materialdesign.domain

import ru.nikitaartamonov.materialdesign.R

class Themes {

    enum class Entities (val styleId: Int) {
        POLICE(R.style.Theme_Police),
        PURPLE_AND_YELLOW(R.style.Theme_PurpleAndYellow)
    }

    companion object {
        const val THEME_KEY = "THEME_KEY"
        val DEFAULT_THEME = Entities.POLICE
        const val THEMES_PREF_NAME = "THEMES_PREF_NAME"
    }
}
