package ru.nikitaartamonov.materialdesign.ui.pages.weather

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import ru.nikitaartamonov.materialdesign.app.App
import ru.nikitaartamonov.materialdesign.data.retrofit.GstWrapper
import ru.nikitaartamonov.materialdesign.domain.GstDataLoadingState
import ru.nikitaartamonov.materialdesign.domain.NasaDataLoader
import ru.nikitaartamonov.materialdesign.domain.notes.Note
import ru.nikitaartamonov.materialdesign.domain.notes.NotesDiffUtil
import java.util.*

class WeatherViewModel : ViewModel() {

    private lateinit var nasaDataLoader: NasaDataLoader
    private var gstData: List<GstWrapper>? = null

    private var notes = listOf(
            Note(Note.id++, "Note 0"),
            Note(Note.id++, "Note 1"),
            Note(Note.id++, "Note 2"),
            Note(Note.id++, "Note 3")
        )

    val setGstDataLiveData: LiveData<String> = MutableLiveData()
    val setNotesRecyclerViewContentLiveData: LiveData<List<Note>> = MutableLiveData()

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

    fun onNoteClick(adapter: NotesAdapter, note: Note) {
        val oldNotes = adapter.requireNotes()
        val noteIndex = oldNotes.indexOf(note)
        oldNotes[noteIndex] = note.copy()
        note.content += "🥸"
        updateListWithDiffUtil(oldNotes, adapter.requireNotes(), adapter)
        notes = adapter.requireNotes()
    }

    fun onItemMoved(from: Int, to: Int, adapter: NotesAdapter) {
        val oldNotes = adapter.requireNotes()
        val newNotes = adapter.requireNotes().apply { Collections.swap(this, from, to) }
        adapter.setList(newNotes)
        updateListWithDiffUtil(oldNotes, newNotes, adapter)
        notes = adapter.requireNotes()
    }

    fun onItemRemoved(position: Int, adapter: NotesAdapter) {
        val oldNotes = adapter.requireNotes()
        val newNotes = adapter.requireNotes().apply { removeAt(position) }
        adapter.setList(newNotes)
        updateListWithDiffUtil(oldNotes, newNotes, adapter)
        notes = adapter.requireNotes()
    }

    private fun updateListWithDiffUtil(
        oldNotes: List<Note>,
        newNotes: List<Note>,
        adapter: NotesAdapter
    ) {
        val diffUtil = NotesDiffUtil(oldNotes, newNotes)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        diffResult.dispatchUpdatesTo(adapter)
    }
}

private fun <T> LiveData<T>.postValue(value: T) {
    (this as MutableLiveData<T>).postValue(value)
}