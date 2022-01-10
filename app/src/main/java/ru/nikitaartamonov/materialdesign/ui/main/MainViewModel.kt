package ru.nikitaartamonov.materialdesign.ui.main

import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.nikitaartamonov.materialdesign.domain.Event
import ru.nikitaartamonov.materialdesign.domain.Screens

class MainViewModel : ViewModel() {

    val openScreenLiveData: LiveData<Event<Screens>> = MutableLiveData()

    fun bottomNavigationViewItemSelected(menuItem: MenuItem) {
        openScreenLiveData.postValue(Event(Screens.getById(menuItem.itemId)))
    }

    fun bottomNavigationViewItemReselected(menuItem: MenuItem) {
        //TODO("Not yet implemented")
    }

}

private fun <T> LiveData<T>.postValue(value: T) {
    (this as MutableLiveData<T>).postValue(value)
}