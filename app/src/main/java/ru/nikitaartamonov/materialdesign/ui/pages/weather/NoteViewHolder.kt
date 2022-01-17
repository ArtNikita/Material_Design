package ru.nikitaartamonov.materialdesign.ui.pages.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nikitaartamonov.materialdesign.databinding.ViewHolderNoteBinding
import ru.nikitaartamonov.materialdesign.domain.Note

class NoteViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    ViewHolderNoteBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    ).root
) {
    private val binding by lazy { ViewHolderNoteBinding.bind(itemView) }

    fun bind(note: Note) {
        binding.noteTextView.text = note.content
    }
}