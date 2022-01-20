package ru.nikitaartamonov.materialdesign.ui.pages.earth_photos

import android.os.Bundle
import android.transition.ChangeImageTransform
import android.transition.TransitionManager
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import ru.nikitaartamonov.materialdesign.R
import ru.nikitaartamonov.materialdesign.databinding.FragmentEarthPhotoBinding

private const val IMAGE_LINK_PART_KEY = "IMAGE_LINK_PART_KEY"
private const val DATE_KEY = "DATE_KEY"

class EarthPhotoFragment : Fragment(R.layout.fragment_earth_photo) {

    private val binding by viewBinding(FragmentEarthPhotoBinding::bind)

    private val args by lazy {
        arguments ?: throw IllegalStateException("Arguments should be provided")
    }
    private val imageLinkPart by lazy {
        args.getString(IMAGE_LINK_PART_KEY)
            ?: throw IllegalStateException("Image link should be provided")
    }
    private val date by lazy { args.getString(DATE_KEY) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImage(imageLinkPart)
        binding.earthPhotoDateTextView.text = date
        initImageViewClickListener()
    }

    private fun initImageViewClickListener() {
        binding.earthPhotoImageView.setOnClickListener {
            TransitionManager.beginDelayedTransition(
                binding.earthPhotoFragmentConstraintLayout,
                ChangeImageTransform()
            )
            val currentScaleType = binding.earthPhotoImageView.scaleType
            val newScaleType = if (currentScaleType == ImageView.ScaleType.FIT_CENTER) {
                ImageView.ScaleType.CENTER_CROP
            } else {
                ImageView.ScaleType.FIT_CENTER
            }
            binding.earthPhotoImageView.scaleType = newScaleType
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