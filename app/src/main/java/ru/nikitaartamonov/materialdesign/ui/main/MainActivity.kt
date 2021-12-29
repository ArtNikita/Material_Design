package ru.nikitaartamonov.materialdesign.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.nikitaartamonov.materialdesign.databinding.ActivityMainBinding
import ru.nikitaartamonov.materialdesign.ui.pages.daily_image.DailyImageFragment

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
        viewModel.initStartFragmentLiveData.observe(this) { event ->
            event.getContentIfNotHandled()?.let { initStartFragment() }
        }
    }

    private fun initStartFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(binding.mainFragmentContainer.id, DailyImageFragment())
            .commit()
    }
}