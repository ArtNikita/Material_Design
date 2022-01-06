package ru.nikitaartamonov.materialdesign.ui.pages.weather

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.nikitaartamonov.materialdesign.app.App
import ru.nikitaartamonov.materialdesign.data.retrofit.GstWrapper
import ru.nikitaartamonov.materialdesign.domain.GstDataLoadingState
import ru.nikitaartamonov.materialdesign.domain.NasaDataLoader

class WeatherViewModel : ViewModel() {

    private lateinit var nasaDataLoader: NasaDataLoader
    private var gstData: List<GstWrapper>? = null

    val setGstDataLiveData: LiveData<String> = MutableLiveData()

    fun onViewCreated(application: Application) {
        nasaDataLoader = (application as App).nasaDataLoader
        if (gstData == null) {
            loadGstData()
        } else {
            gstData?.let { setGstDataLiveData.postValue(generateGstListString(it)) }
        }
    }

    private fun loadGstData() {
        nasaDataLoader.loadGstData { state ->
            when (state) {
                is GstDataLoadingState.Success -> {
                    gstData = state.gstData
                    setGstDataLiveData.postValue(generateGstListString(state.gstData))
                }
                is GstDataLoadingState.Error -> {
                    //todo notify about loading error
                }
            }
        }
    }

    private fun generateGstListString(gstData: List<GstWrapper>): String {
        val stringBuilder = StringBuilder()
        for (i in gstData.size - 1 downTo 0) {
            stringBuilder
                .append(gstData.size - i)
                .append(". ")
                .append(gstData[i].startTime)
            if (i == 0) continue
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }
}

private fun <T> LiveData<T>.postValue(value: T) {
    (this as MutableLiveData<T>).postValue(value)
}