package ru.nikitaartamonov.materialdesign.ui.pages.weather

import android.app.Application
import android.os.Build
import android.text.Spannable
import android.text.style.BulletSpan
import androidx.core.text.toSpannable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.nikitaartamonov.materialdesign.R
import ru.nikitaartamonov.materialdesign.app.App
import ru.nikitaartamonov.materialdesign.data.retrofit.GstWrapper
import ru.nikitaartamonov.materialdesign.domain.Event
import ru.nikitaartamonov.materialdesign.domain.GstDataLoadingState
import ru.nikitaartamonov.materialdesign.domain.NasaDataLoader
import ru.nikitaartamonov.materialdesign.domain.notes.Note
import java.util.*

class WeatherViewModel : ViewModel() {

    private lateinit var nasaDataLoader: NasaDataLoader
    private var gstData: List<GstWrapper>? = null

    private var notes = listOf(
        Note(Note.id++, "Note ${Note.id}"),
        Note(Note.id++, "Note ${Note.id}"),
        Note(Note.id++, "Note ${Note.id}"),
        Note(Note.id++, "Note ${Note.id}")
    )

    val setGstDataLiveData: LiveData<String> = MutableLiveData()
    val updateListLiveData: LiveData<Event<List<Note>>> = MutableLiveData()

    fun onViewCreated(application: Application) {
        nasaDataLoader = (application as App).nasaDataLoader
        val currentGstData = gstData
        if (currentGstData == null) {
            loadGstData()
        } else {
            setGstDataLiveData.postValue(generateGstListText(currentGstData))
        }
        updateListLiveData.postValue(Event(notes))
    }

    private fun loadGstData() {
        nasaDataLoader.loadGstData { state ->
            when (state) {
                is GstDataLoadingState.Success -> {
                    gstData = state.gstData
                    setGstDataLiveData.postValue(generateGstListText(state.gstData))
                }
                is GstDataLoadingState.Error -> {
                    //todo notify about loading error
                }
            }
        }
    }

    private fun generateGstListText(gstData: List<GstWrapper>): CharSequence {
        val stringBuilder = StringBuilder()
        gstData.reversed().forEach { gstItem ->
            stringBuilder.append(gstItem.startTime).append("\n")
        }
        val spannable = stringBuilder.toString().toSpannable()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            for (i in 0..stringBuilder.length step gstData[0].startTime.length + 1)
                spannable.setSpan(
                    BulletSpan(4, R.attr.colorSecondary), i, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
        }
        return spannable
    }

    fun onNoteClick(note: Note) {
        val newNotes = notes.toMutableList()
        val noteIndex = notes.indexOf(note)
        newNotes[noteIndex] = note.copy().apply { content += "🥸" }
        updateListLiveData.postValue(Event(newNotes))
        notes = newNotes
    }

    fun onItemMoved(from: Int, to: Int) {
        val newNotes = notes.toMutableList()
        Collections.swap(newNotes, from, to)
        updateAndSaveList(newNotes)
    }

    fun onItemRemoved(position: Int) {
        val newNotes = notes.toMutableList()
        newNotes.removeAt(position)
        updateAndSaveList(newNotes)
    }

    fun addFabPressed() {
        val newNote = Note(Note.id++, "Note ${Note.id}")
        val newNotes = notes.toMutableList()
        newNotes += newNote
        updateAndSaveList(newNotes)
    }

    private fun updateAndSaveList(
        newNotes: List<Note>
    ) {
        updateListLiveData.postValue(Event(newNotes))
        notes = newNotes
    }
}

private fun <T> LiveData<T>.postValue(value: T) {
    (this as MutableLiveData<T>).postValue(value)
}