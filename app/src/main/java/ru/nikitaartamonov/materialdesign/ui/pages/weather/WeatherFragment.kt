package ru.nikitaartamonov.materialdesign.ui.pages.weather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import ru.nikitaartamonov.materialdesign.R
import ru.nikitaartamonov.materialdesign.databinding.FragmentWeatherBinding

private const val IMAGE_URL =
    "https://images.pexels.com/photos/3617500/pexels-photo-3617500.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"

class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val binding by viewBinding(FragmentWeatherBinding::bind)
    private val viewModel by viewModels<WeatherViewModel>()

    private val adapter by lazy { NotesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadToolbarImage()
        viewModel.onViewCreated(requireActivity().application)
        initNotesRecyclerView()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.setGstDataLiveData.observe(viewLifecycleOwner) { gstList ->
            binding.gstListTextView.text = gstList
        }
        viewModel.setNotesRecyclerViewContentLiveData.observe(viewLifecycleOwner) { notes ->
            adapter.setListAndNotify(notes)
        }
    }

    private fun initNotesRecyclerView() {
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notesRecyclerView.adapter = adapter
    }

    private fun loadToolbarImage() {
        Glide
            .with(requireContext())
            .load(IMAGE_URL)
            .into(binding.weatherToolbarImage)
    }
}