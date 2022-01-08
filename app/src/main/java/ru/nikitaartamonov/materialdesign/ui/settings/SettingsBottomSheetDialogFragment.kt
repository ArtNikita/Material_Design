package ru.nikitaartamonov.materialdesign.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.nikitaartamonov.materialdesign.R
import ru.nikitaartamonov.materialdesign.databinding.FragmentSettingsBottomSheetDialogBinding
import ru.nikitaartamonov.materialdesign.utils.Themes

class SettingsBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var binding: FragmentSettingsBottomSheetDialogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBottomSheetDialogBinding.inflate(inflater, container, false)
        return requireBinding().root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initThemesNavigationView()
    }

    private fun initThemesNavigationView() {
        requireBinding().themesNavigationView.setNavigationItemSelectedListener { menuItem ->
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
        Themes.setTheme(theme, requireActivity())
    }

    private fun requireBinding(): FragmentSettingsBottomSheetDialogBinding {
        return binding ?: throw IllegalStateException("binding is null")
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}