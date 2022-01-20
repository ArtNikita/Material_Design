package ru.nikitaartamonov.materialdesign.ui.pages.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nikitaartamonov.materialdesign.databinding.ViewHolderNoteBinding
import ru.nikitaartamonov.materialdesign.domain.notes.Note
import ru.nikitaartamonov.materialdesign.domain.notes.NoteClickListener

class NoteViewHolder(parent: ViewGroup, private val listener: NoteClickListener) : RecyclerView.ViewHolder(
    ViewHolderNoteBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    ).root
) {

    private val binding by lazy { ViewHolderNoteBinding.bind(itemView) }
    private lateinit var note: Note

    init {
        itemView.setOnClickListener {
            listener.onClick(note)
        }
    }

    fun bind(note: Note) {
        this.note = note
        binding.noteTextView.text = note.content
    }
}