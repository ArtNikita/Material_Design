package ru.nikitaartamonov.materialdesign.domain

interface NasaDataLoader {

    fun loadImage(callback: (ImageLoadingState) -> Unit)

    fun loadEarthPhotos(callback: (EarthPhotosLoadingState) -> Unit)

    fun loadGstData(callback: (GstDataLoadingState) -> Unit)
}