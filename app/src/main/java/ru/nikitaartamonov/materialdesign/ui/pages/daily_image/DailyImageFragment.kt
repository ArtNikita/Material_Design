package ru.nikitaartamonov.materialdesign.ui.pages.daily_image

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.nikitaartamonov.materialdesign.R
import ru.nikitaartamonov.materialdesign.data.retrofit.ImageWrapper
import ru.nikitaartamonov.materialdesign.databinding.FragmentDailyImageBinding
import ru.nikitaartamonov.materialdesign.ui.settings.SettingsBottomSheetDialogFragment

class DailyImageFragment : Fragment(R.layout.fragment_daily_image) {

    private val binding: FragmentDailyImageBinding by viewBinding(FragmentDailyImageBinding::bind)
    private val viewModel by viewModels<DailyImageViewModel>()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
        viewModel.onViewIsReady()
    }

    private fun initViewModel() {
        viewModel.renderImageDataLiveData.observe(viewLifecycleOwner) { imageWrapper ->
            renderImageData(imageWrapper)
        }
        viewModel.bottomSheetStateLiveData.observe(viewLifecycleOwner) { currentState ->
            bottomSheetBehavior.state = currentState
        }
        viewModel.searchInWikiLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { stringUrl ->
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(stringUrl))
                startActivity(browserIntent)
            }
        }
        viewModel.openDescriptionLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { openDescription() }
        }
        viewModel.openSettingsLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { openSettings() }
        }
    }

    private fun openSettings() {
        SettingsBottomSheetDialogFragment().show(
            requireActivity().supportFragmentManager,
            SettingsBottomSheetDialogFragment.TAG
        )
    }

    private fun openDescription() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun renderImageData(imageWrapper: ImageWrapper) {
        Glide
            .with(requireContext())
            .load(if (binding.hdQualityChip.isChecked) imageWrapper.hdUrl else imageWrapper.url)
            .into(binding.dailyImageView)
        binding.bottomSheet.titleTextView.text = imageWrapper.title
        binding.bottomSheet.copyrightTextView.text = imageWrapper.copyright
        binding.bottomSheet.imageDescriptionTextView.text = imageWrapper.explanation
    }

    private fun initViews() {
        initBottomSheet()
        initWikipediaEditText()
        initQualityChip()
        initDescriptionChip()
        initSettingsChip()
    }

    private fun initSettingsChip() {
        binding.settingsChip.setOnClickListener { viewModel.settingsChipClicked() }
    }

    private fun initDescriptionChip() {
        binding.descriptionChip.setOnClickListener { viewModel.descriptionChipClicked() }
    }

    private fun initQualityChip() {
        binding.hdQualityChip.setOnClickListener { viewModel.qualityChipTouched() }
    }

    private fun initBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.bottomSheetContainer)
        bottomSheetBehavior.addBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    viewModel.onBottomSheetStateChanged(newState)
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    //do nothing
                }
            }
        )
    }

    private fun initWikipediaEditText() {
        binding.wikipediaTextInputLayout.setEndIconOnClickListener {
            val textToSearch = binding.wikipediaTextInputEditText.text.toString()
            viewModel.onWikiIconClicked(textToSearch)
        }
    }
}