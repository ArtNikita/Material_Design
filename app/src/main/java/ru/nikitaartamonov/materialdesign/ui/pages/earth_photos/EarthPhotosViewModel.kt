package ru.nikitaartamonov.materialdesign.ui.pages.earth_photos

import android.app.Application
import androidx.lifecycle.ViewModel
import ru.nikitaartamonov.materialdesign.app.App
import ru.nikitaartamonov.materialdesign.domain.ImageLoader

class EarthPhotosViewModel : ViewModel() {

    private lateinit var imageLoader: ImageLoader

    fun onViewIsReady(app: Application) {
        imageLoader = (app as App).imageLoader

    }
}