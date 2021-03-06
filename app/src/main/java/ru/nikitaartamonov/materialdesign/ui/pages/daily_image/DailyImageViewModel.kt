package ru.nikitaartamonov.materialdesign.ui.pages.daily_image

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.nikitaartamonov.materialdesign.app.App
import ru.nikitaartamonov.materialdesign.data.retrofit.ImageWrapper
import ru.nikitaartamonov.materialdesign.domain.Event
import ru.nikitaartamonov.materialdesign.domain.NasaDataLoader
import ru.nikitaartamonov.materialdesign.domain.ImageLoadingState

class DailyImageViewModel : ViewModel() {

    private lateinit var nasaDataLoader: NasaDataLoader
    private var imageWrapper: ImageWrapper? = null
    private var bottomSheetCurrentState = BottomSheetBehavior.STATE_COLLAPSED

    val renderImageDataLiveData: LiveData<ImageWrapper> = MutableLiveData()
    val bottomSheetStateLiveData: LiveData<Int> = MutableLiveData()
    val searchInWikiLiveData: LiveData<Event<String>> = MutableLiveData()
    val openDescriptionLiveData: LiveData<Event<Boolean>> = MutableLiveData()
    val openSettingsLiveData: LiveData<Event<Boolean>> = MutableLiveData()

    fun onViewIsReady(app: Application) {
        nasaDataLoader = (app as App).nasaDataLoader
        if (imageWrapper == null) {
            loadImage()
        } else {
            imageWrapper?.apply { renderImageDataLiveData.postValue(this) }
        }
        bottomSheetStateLiveData.postValue(bottomSheetCurrentState)
    }

    fun onBottomSheetStateChanged(newState: Int) {
        bottomSheetCurrentState = newState
    }

    fun onWikiIconClicked(inputText: String) {
        val textToSearch = inputText.trim()
        if (textToSearch.isEmpty()) {
            return
        }
        val event = Event("https://ru.wikipedia.org/w/index.php?search=$textToSearch")
        searchInWikiLiveData.postValue(event)
    }

    private fun loadImage() {
        nasaDataLoader.loadImage { state ->
            when (state) {
                is ImageLoadingState.Error -> {
                    //todo notify about loading error
                }
                is ImageLoadingState.Success -> {
                    imageWrapper = state.imageWrapper
                    renderImageDataLiveData.postValue(state.imageWrapper)
                }
            }
        }
    }

    fun qualityChipTouched() {
        imageWrapper?.let { renderImageDataLiveData.postValue(it) }
    }

    fun settingsChipClicked() {
        openSettingsLiveData.postValue(Event(true))
    }

    fun descriptionChipClicked() {
        openDescriptionLiveData.postValue(Event(true))
    }
}

private fun <T> LiveData<T>.postValue(value: T) {
    (this as MutableLiveData<T>).postValue(value)
}