package ru.nikitaartamonov.materialdesign.ui.pages.earth_photos

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.nikitaartamonov.materialdesign.app.App
import ru.nikitaartamonov.materialdesign.data.retrofit.EarthPhotoWrapper
import ru.nikitaartamonov.materialdesign.domain.EarthPhotosLoadingState
import ru.nikitaartamonov.materialdesign.domain.Event
import ru.nikitaartamonov.materialdesign.domain.NasaDataLoader

class EarthPhotosViewModel : ViewModel() {

    private lateinit var nasaDataLoader: NasaDataLoader
    private var earthPhotos: List<EarthPhotoWrapper>? = null

    val initViewPagerLiveData: LiveData<Event<List<EarthPhotoWrapper>>> = MutableLiveData()

    fun onViewIsReady(app: Application) {
        nasaDataLoader = (app as App).nasaDataLoader
        if (earthPhotos == null) {
            loadPhotos()
        } else {
            earthPhotos?.let { initViewPagerLiveData.postValue(Event(it)) }
        }
    }

    private fun loadPhotos() {
        nasaDataLoader.loadEarthPhotos { state ->
            when (state) {
                is EarthPhotosLoadingState.Success -> {
                    initViewPagerLiveData.postValue(Event(state.earthImages))
                }
                is EarthPhotosLoadingState.Error -> {
                    //todo
                }
            }
        }
    }
}

private fun <T> LiveData<T>.postValue(value: T) {
    (this as MutableLiveData<T>).postValue(value)
}