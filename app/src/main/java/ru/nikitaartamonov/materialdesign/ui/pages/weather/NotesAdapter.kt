package ru.nikitaartamonov.materialdesign.ui.pages.weather

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nikitaartamonov.materialdesign.domain.notes.Note
import ru.nikitaartamonov.materialdesign.domain.notes.NoteClickListener

class NotesAdapter : RecyclerView.Adapter<NoteViewHolder>() {

    private var notesList: List<Note> = emptyList()
    private lateinit var listener: NoteClickListener

    fun setListener(listener: NoteClickListener) {
        this.listener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(notes: List<Note>) {
        notesList = notes
        notifyDataSetChanged()
    }

    fun setList(notes: List<Note>) {
        notesList = notes
    }

    fun requireNotes() = notesList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent, listener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getNote(position))
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    private fun getNote(position: Int): Note {
        return notesList[position]
    }
}