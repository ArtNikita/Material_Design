package ru.nikitaartamonov.materialdesign.ui.pages.earth_photos

import android.app.Application
import androidx.lifecycle.ViewModel
import ru.nikitaartamonov.materialdesign.app.App
import ru.nikitaartamonov.materialdesign.data.retrofit.EarthPhotoWrapper
import ru.nikitaartamonov.materialdesign.domain.EarthPhotosLoadingState
import ru.nikitaartamonov.materialdesign.domain.ImageLoader

class EarthPhotosViewModel : ViewModel() {

    private lateinit var imageLoader: ImageLoader
    private var earthPhotos: List<EarthPhotoWrapper>? = null

    fun onViewIsReady(app: Application) {
        imageLoader = (app as App).imageLoader
        if (earthPhotos == null) {
            loadPhotos()
        } else {
            //todo
        }
    }

    private fun loadPhotos() {
        imageLoader.loadEarthPhotos { state ->
            when (state) {
                is EarthPhotosLoadingState.Success -> {
                    //todo
                }
                is EarthPhotosLoadingState.Error -> {
                    //todo
                }
            }
        }
    }
}