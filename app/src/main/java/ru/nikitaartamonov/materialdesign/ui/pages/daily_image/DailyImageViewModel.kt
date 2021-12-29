package ru.nikitaartamonov.materialdesign.ui.pages.daily_image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.nikitaartamonov.materialdesign.data.retrofit.ImageLoaderRetrofit
import ru.nikitaartamonov.materialdesign.data.retrofit.ImageWrapper
import ru.nikitaartamonov.materialdesign.domain.Event
import ru.nikitaartamonov.materialdesign.domain.ImageLoader

class DailyImageViewModel : ViewModel() {

    private val imageLoader: ImageLoader = ImageLoaderRetrofit()
    private var imageWrapper: ImageWrapper? = null
    private var bottomSheetCurrentState = BottomSheetBehavior.STATE_COLLAPSED

    val renderImageDataLiveData: LiveData<ImageWrapper> = MutableLiveData()
    val bottomSheetStateLiveData: LiveData<Int> = MutableLiveData()
    val searchInWikiLiveData: LiveData<Event<String>> = MutableLiveData()

    fun onViewIsReady() {
        loadImage()
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
        searchInWikiLiveData.postValue(Event("https://ru.wikipedia.org/w/index.php?search=$textToSearch"))
    }

    private fun loadImage() {
        imageLoader.loadImage { state ->
            when (state) {
                is ImageLoader.State.Error -> {
                    //todo notify about loading error
                }
                is ImageLoader.State.Success -> {
                    imageWrapper = state.imageWrapper
                    renderImageDataLiveData.postValue(state.imageWrapper)
                }
            }
        }
    }

    fun qualityChipTouched() {
        imageWrapper?.let { renderImageDataLiveData.postValue(it) }
    }
}

private fun <T> LiveData<T>.postValue(value: T) {
    (this as MutableLiveData<T>).postValue(value)
}