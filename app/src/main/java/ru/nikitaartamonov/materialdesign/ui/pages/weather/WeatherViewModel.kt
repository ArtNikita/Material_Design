package ru.nikitaartamonov.materialdesign.ui.pages.weather

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    val setNotesRecyclerViewContentLiveData: LiveData<List<Note>> = MutableLiveData()
    val updateListWithDiffUtilLiveData: LiveData<Event<List<Note>>> = MutableLiveData()

    fun onViewCreated(application: Application) {
        nasaDataLoader = (application as App).nasaDataLoader
        val currentGstData = gstData
        if (currentGstData == null) {
            loadGstData()
        } else {
            setGstDataLiveData.postValue(generateGstListString(currentGstData))
        }
        setNotesRecyclerViewContentLiveData.postValue(notes)
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
        gstData.reversed().forEachIndexed { index, gstItem ->
            stringBuilder
                .append(index + 1)
                .append(". ")
                .append(gstItem.startTime)
            if (index != gstData.size - 1) {
                stringBuilder.append("\n")
            }
        }
        return stringBuilder.toString()
    }

    fun onNoteClick(oldNotes: MutableList<Note>, note: Note) {
        val newNotes = oldNotes.toMutableList()
        val noteIndex = oldNotes.indexOf(note)
        newNotes[noteIndex] = note.copy().apply { content += "🥸" }
        updateListWithDiffUtilLiveData.postValue(Event(newNotes))
        notes = newNotes
    }

    fun onItemMoved(from: Int, to: Int, oldNotes: MutableList<Note>) {
        val newNotes = oldNotes.toMutableList()
        Collections.swap(newNotes, from, to)
        updateAndSaveList(newNotes)
    }

    fun onItemRemoved(position: Int, oldNotes: MutableList<Note>) {
        val newNotes = oldNotes.toMutableList()
        newNotes.removeAt(position)
        updateAndSaveList(newNotes)
    }

    fun addFabPressed(oldNotes: MutableList<Note>) {
        val newNote = Note(Note.id++, "Note ${Note.id}")
        val newNotes = oldNotes.toMutableList()
        newNotes += newNote
        updateAndSaveList(newNotes)
    }

    private fun updateAndSaveList(
        newNotes: List<Note>
    ) {
        updateListWithDiffUtilLiveData.postValue(Event(newNotes))
        notes = newNotes
    }
}

private fun <T> LiveData<T>.postValue(value: T) {
    (this as MutableLiveData<T>).postValue(value)
}