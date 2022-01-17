package ru.nikitaartamonov.materialdesign.ui.pages.weather

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nikitaartamonov.materialdesign.domain.Note

class NotesAdapter : RecyclerView.Adapter<NoteViewHolder>() {
    private var notesList: List<Note> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setListAndNotify(notes: List<Note>) {
        notesList = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getNote(position))
    }

    override fun getItemCount(): Int = notesList.size

    private fun getNote(position: Int) = notesList[position]
}