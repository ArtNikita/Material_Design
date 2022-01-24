package ru.nikitaartamonov.materialdesign.ui.pages.weather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import ru.nikitaartamonov.materialdesign.R
import ru.nikitaartamonov.materialdesign.databinding.FragmentWeatherBinding
import ru.nikitaartamonov.materialdesign.domain.notes.ItemDragTouchHelperCallback
import ru.nikitaartamonov.materialdesign.domain.notes.Note
import ru.nikitaartamonov.materialdesign.domain.notes.NoteClickListener
import ru.nikitaartamonov.materialdesign.domain.notes.NotesDiffUtil

private const val TOOLBAR_PLUG_IMAGE_URL =
    "https://images.pexels.com/photos/3617500/pexels-photo-3617500.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"

class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val binding by viewBinding(FragmentWeatherBinding::bind)
    private val viewModel by viewModels<WeatherViewModel>()

    private val adapter by lazy {
        NotesAdapter().apply {
            setListener(object : NoteClickListener {
                override fun onClick(note: Note) {
                    viewModel.onNoteClick(requireNotes(), note)
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadToolbarImage()

        viewModel.onViewCreated(requireActivity().application)

        initNotesRecyclerView()

        initViewModel()

        binding.addFloatingActionButton.setOnClickListener { viewModel.addFabPressed(adapter.requireNotes()) }
    }

    private fun initViewModel() {
        viewModel.setGstDataLiveData.observe(viewLifecycleOwner) { gstList ->
            binding.gstListTextView.text = gstList
        }
        viewModel.setNotesRecyclerViewContentLiveData.observe(viewLifecycleOwner) { notes ->
            adapter.updateItems(notes)
        }
        viewModel.updateListWithDiffUtilLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { newNotes ->
                val oldNotes = adapter.requireNotes()
                adapter.setList(newNotes)
                updateListWithDiffUtil(oldNotes, newNotes)
            }
        }
    }

    private fun initNotesRecyclerView() {
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notesRecyclerView.adapter = adapter
        val itemDragTouchHelperCallback = ItemDragTouchHelperCallback(
            onItemMove = { from, to -> viewModel.onItemMoved(from, to, adapter.requireNotes()) },
            onItemSwiped = { position -> viewModel.onItemRemoved(position, adapter.requireNotes()) }
        )
        ItemTouchHelper(itemDragTouchHelperCallback).attachToRecyclerView(binding.notesRecyclerView)
    }

    private fun loadToolbarImage() {
        Glide
            .with(requireContext())
            .load(TOOLBAR_PLUG_IMAGE_URL)
            .into(binding.weatherToolbarImage)
    }

    private fun updateListWithDiffUtil(
        oldNotes: List<Note>,
        newNotes: List<Note>
    ) {
        val diffUtil = NotesDiffUtil(oldNotes, newNotes)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        diffResult.dispatchUpdatesTo(adapter)
    }
}