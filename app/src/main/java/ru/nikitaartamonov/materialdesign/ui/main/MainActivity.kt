package ru.nikitaartamonov.materialdesign.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.nikitaartamonov.materialdesign.databinding.ActivityMainBinding
import ru.nikitaartamonov.materialdesign.domain.Screens
import ru.nikitaartamonov.materialdesign.domain.Themes
import ru.nikitaartamonov.materialdesign.ui.pages.daily_image.DailyImageFragment

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(getCurrentTheme())
        setContentView(binding.root)

        if (savedInstanceState == null) {
            openScreen()
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

    private fun openScreen(screen: Screens = Screens.DAILY_IMAGE) {
        val fragmentToOpen: Fragment = when (screen) {
            Screens.DAILY_IMAGE -> {
                DailyImageFragment()
            }
            Screens.EARTH_PHOTOS -> {
                //todo
                DailyImageFragment()
            }
            Screens.WEATHER -> {
                //todo
                DailyImageFragment()
            }
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

    private fun getCurrentTheme(): Int {
        val currentTheme: String = getPreferences(MODE_PRIVATE).getString(
            Themes.THEME_KEY,
            Themes.DEFAULT_THEME.toString()
        ) ?: Themes.DEFAULT_THEME.toString()
        return Themes.Entities.valueOf(currentTheme).styleId
    }
}