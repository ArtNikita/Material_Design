package ru.nikitaartamonov.materialdesign.ui.pages.earth_photos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.nikitaartamonov.materialdesign.R
import ru.nikitaartamonov.materialdesign.databinding.FragmentEarthPhotosBinding

class EarthPhotosFragment : Fragment(R.layout.fragment_earth_photos) {

    private val binding by viewBinding(FragmentEarthPhotosBinding::bind)
    private val viewModel by viewModels<EarthPhotosViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }

    private fun initViewPager() {
        //todo
    }
}