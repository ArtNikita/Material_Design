package ru.nikitaartamonov.materialdesign.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.nikitaartamonov.materialdesign.domain.Event

class MainViewModel : ViewModel() {

    private var viewJustLaunched = true

    val initStartFragmentLiveData: LiveData<Event<Boolean>> = MutableLiveData()

    fun onViewIsReady() {
        if (viewJustLaunched) {
            viewJustLaunched = false
            initStartFragmentLiveData.postValue(Event(true))
        }
    }
}

private fun <T> LiveData<T>.postValue(value: T) {
    (this as MutableLiveData<T>).postValue(value)
}