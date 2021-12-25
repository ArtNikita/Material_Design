package ru.nikitaartamonov.materialdesign.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.nikitaartamonov.materialdesign.data.retrofit.ImageLoaderRetrofit
import ru.nikitaartamonov.materialdesign.data.retrofit.ImageWrapper
import ru.nikitaartamonov.materialdesign.domain.ImageLoader

class MainViewModel : ViewModel() {
    private val imageLoader: ImageLoader = ImageLoaderRetrofit()
    private var imageWrapper: ImageWrapper? = null
    private var bottomSheetCurrentState = BottomSheetBehavior.STATE_COLLAPSED

    val renderImageDataLiveData: LiveData<ImageWrapper> = MutableLiveData()
    val bottomSheetStateLiveData: LiveData<Int> = MutableLiveData()

    fun onViewIsReady() {
        loadImage()
        bottomSheetStateLiveData.postValue(bottomSheetCurrentState)
    }

    fun onBottomSheetStateChanged(newState: Int) {
        bottomSheetCurrentState = newState
    }

    private fun loadImage() {
        imageLoader.loadImage { loadedImageWrapper ->
            if (loadedImageWrapper == null) {
                //todo notify about loading error
            } else {
                imageWrapper = loadedImageWrapper
                renderImageDataLiveData.postValue(loadedImageWrapper)
            }
        }
    }

    private fun <T> LiveData<T>.postValue(value: T) {
        (this as MutableLiveData<T>).postValue(value)
    }

}