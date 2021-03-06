package ru.nikitaartamonov.materialdesign.ui.pages.earth_photos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import ru.nikitaartamonov.materialdesign.R
import ru.nikitaartamonov.materialdesign.databinding.FragmentEarthPhotoBinding

private const val IMAGE_LINK_PART_KEY = "IMAGE_LINK_PART_KEY"
private const val DATE_KEY = "DATE_KEY"

class EarthPhotoFragment : Fragment(R.layout.fragment_earth_photo) {

    private val binding by viewBinding(FragmentEarthPhotoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { args ->
            val imageLinkPart = args.getString(IMAGE_LINK_PART_KEY)
                ?: throw IllegalStateException("Image link should be provided")
            val date = args.getString(DATE_KEY)
            setImage(imageLinkPart)
            binding.earthPhotoDateTextView.text = date
        }

    }

    private fun setImage(imageLinkPart: String) {
        val photoUrl = getEarthPhotoUrl(imageLinkPart)
        Glide
            .with(requireContext())
            .load(photoUrl)
            .into(binding.earthPhotoImageView)
    }

    private fun getEarthPhotoUrl(imageLinkPart: String): String {
        val datePart = imageLinkPart.split("_")[2]
        val year = datePart.substring(0, 4)
        val month = datePart.substring(4, 6)
        val day = datePart.substring(6, 8)
        return "https://epic.gsfc.nasa.gov/archive/natural/$year/$month/$day/png/$imageLinkPart.png"
    }

    companion object {

        fun newInstance(imageLinkPart: String, date: String): EarthPhotoFragment {
            val args = Bundle().apply {
                putString(IMAGE_LINK_PART_KEY, imageLinkPart)
                putString(DATE_KEY, date)
            }
            val fragment = EarthPhotoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}