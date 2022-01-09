package ru.nikitaartamonov.materialdesign.domain

import ru.nikitaartamonov.materialdesign.R

enum class Screens(val menuId: Int) {
    DAILY_IMAGE(R.id.daily_image_fragment_menu),
    EARTH_PHOTOS(R.id.earth_fragment_menu),
    WEATHER(R.id.weather_fragment_menu);

    companion object {
        fun getById(id: Int): Screens {
            for (screen in values()) {
                if (screen.menuId == id) {
                    return screen
                }
            }
            throw IllegalStateException("No such menu id")
        }
    }
}