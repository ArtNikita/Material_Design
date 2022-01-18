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

class WeatherViewModel : ViewModel() {

    private lateinit var nasaDataLoader: NasaDataLoader
    private var gstData: List<GstWrapper>? = null

    private val notes by lazy {
        listOf(
            Note(Note.id++, "Note 0"),
            Note(Note.id++, "Note 1")
        )
    }

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
        val diffUtil = NotesDiffUtil(oldNotes, adapter.requireNotes())
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        diffResult.dispatchUpdatesTo(adapter)
    }
}

private fun <T> LiveData<T>.postValue(value: T) {
    (this as MutableLiveData<T>).postValue(value)
}