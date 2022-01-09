package ru.nikitaartamonov.materialdesign.ui.pages.earth_photos

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.nikitaartamonov.materialdesign.data.retrofit.EarthPhotoWrapper

class EarthPhotosViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    lateinit var items: List<EarthPhotoWrapper>

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        val item = items[position]
        return EarthPhotoFragment.newInstance(item.image, item.date)
    }
}