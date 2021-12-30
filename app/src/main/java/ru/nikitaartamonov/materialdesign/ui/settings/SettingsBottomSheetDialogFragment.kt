package ru.nikitaartamonov.materialdesign.ui.settings

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.nikitaartamonov.materialdesign.R
import ru.nikitaartamonov.materialdesign.databinding.FragmentSettingsBottomSheetDialogBinding
import ru.nikitaartamonov.materialdesign.domain.Themes
import ru.nikitaartamonov.materialdesign.domain.Themes.Companion.DEFAULT_THEME
import ru.nikitaartamonov.materialdesign.domain.Themes.Companion.THEME_KEY

class SettingsBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSettingsBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    private val sharedPreferences: SharedPreferences by lazy {
        requireActivity().getPreferences(MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        initThemesNavigationView()
    }

    private fun initThemesNavigationView() {
        binding.themesNavigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.police_theme_menu -> {
                    setTheme(Themes.Entities.POLICE)
                    true
                }
                R.id.purple_and_yellow_menu -> {
                    setTheme(Themes.Entities.PURPLE_AND_YELLOW)
                    true
                }
                else -> throw IllegalStateException("No such menu")
            }
        }
    }

    private fun setTheme(theme: Themes.Entities) {
        val currentTheme = sharedPreferences.getString(THEME_KEY, DEFAULT_THEME.toString())
        if (currentTheme == theme.toString()) return
        sharedPreferences.edit().let {
            it.putString(THEME_KEY, theme.toString())
            it.commit()
        }
        requireActivity().recreate()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        const val TAG = "SettingsBottomSheetDialogFragment"
    }
}