package ru.nikitaartamonov.materialdesign.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import ru.nikitaartamonov.materialdesign.data.retrofit.ImageWrapper
import ru.nikitaartamonov.materialdesign.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        viewModel.onViewIsReady()
    }

    private fun initViewModel() {
        viewModel.renderImageDataLiveData.observe(this) {
            renderImageData(it)
        }
    }

    private fun renderImageData(imageWrapper: ImageWrapper) {
        Glide
            .with(this)
            .load(imageWrapper.url)
            .into(binding.dailyImageView)
    }
}