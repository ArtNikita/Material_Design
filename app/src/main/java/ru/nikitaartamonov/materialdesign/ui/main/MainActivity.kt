package ru.nikitaartamonov.materialdesign.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.nikitaartamonov.materialdesign.data.retrofit.ImageWrapper
import ru.nikitaartamonov.materialdesign.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomSheet()
        initViewModel()
        viewModel.onViewIsReady()
    }

    private fun initViewModel() {
        viewModel.renderImageDataLiveData.observe(this) {
            renderImageData(it)
        }
        viewModel.bottomSheetStateLiveData.observe(this) { currentState ->
            bottomSheetBehavior.state = currentState
        }
    }

    private fun renderImageData(imageWrapper: ImageWrapper) {
        Glide
            .with(this)
            .load(imageWrapper.url)
            .into(binding.dailyImageView)
        binding.bottomSheet.titleTextView.text = imageWrapper.title
        binding.bottomSheet.copyrightTextView.text = imageWrapper.copyright
        binding.bottomSheet.imageDescriptionTextView.text = imageWrapper.explanation
    }

    private fun initBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.bottomSheetContainer)
        bottomSheetBehavior.addBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    viewModel.onBottomSheetStateChanged(newState)
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            }
        )
    }
}