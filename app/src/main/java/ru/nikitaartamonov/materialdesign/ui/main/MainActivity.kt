package ru.nikitaartamonov.materialdesign.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.nikitaartamonov.materialdesign.databinding.ActivityMainBinding
import ru.nikitaartamonov.materialdesign.domain.Screens
import ru.nikitaartamonov.materialdesign.ui.pages.daily_image.DailyImageFragment
import ru.nikitaartamonov.materialdesign.ui.pages.earth_photos.EarthPhotosFragment
import ru.nikitaartamonov.materialdesign.ui.pages.weather.WeatherFragment
import ru.nikitaartamonov.materialdesign.utils.ThemePreferences

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(ThemePreferences.getCurrentTheme(this))
        setContentView(binding.root)

        if (savedInstanceState == null) {
            openScreen(Screens.DAILY_IMAGE)
        }
        initViewModel()
        initBottomNavigationView()
    }

    private fun initViewModel() {
        viewModel.openScreenLiveData.observe(this) { event ->
            event.getContentIfNotHandled()?.let { screen ->
                openScreen(screen)
            }
        }
    }

    private fun openScreen(screen: Screens) {
        val fragmentToOpen = when (screen) {
            Screens.DAILY_IMAGE -> DailyImageFragment()
            Screens.EARTH_PHOTOS -> EarthPhotosFragment()
            Screens.WEATHER -> WeatherFragment()
        }
        supportFragmentManager
            .beginTransaction()
            .replace(binding.mainFragmentContainer.id, fragmentToOpen)
            .commit()
    }

    private fun initBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            viewModel.bottomNavigationViewItemSelected(menuItem)
            true
        }
        binding.bottomNavigationView.setOnItemReselectedListener { menuItem ->
            viewModel.bottomNavigationViewItemReselected(menuItem)
        }
    }
}