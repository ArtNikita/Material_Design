package ru.nikitaartamonov.materialdesign.data.retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nikitaartamonov.materialdesign.BuildConfig
import ru.nikitaartamonov.materialdesign.domain.EarthPhotosLoadingState
import ru.nikitaartamonov.materialdesign.domain.ImageLoader
import ru.nikitaartamonov.materialdesign.domain.ImageLoadingState

private const val BASE_URL = "https://api.nasa.gov/"

class ImageLoaderRetrofit : ImageLoader {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val api: ImageApi by lazy { retrofit.create(ImageApi::class.java) }

    override fun loadImage(callback: (ImageLoadingState) -> Unit) {
        api.getImage(BuildConfig.NASA_API_KEY).enqueue(object : Callback<ImageWrapper> {
            override fun onResponse(call: Call<ImageWrapper>, response: Response<ImageWrapper>) {
                val body = response.body()
                if (body == null) {
                    callback(ImageLoadingState.Error(Throwable("Server problem")))
                } else {
                    callback(ImageLoadingState.Success(body))
                }
            }

            override fun onFailure(call: Call<ImageWrapper>, throwable: Throwable) {
                callback(ImageLoadingState.Error(throwable))
            }
        })
    }

    override fun loadEarthPhotos(callback: (EarthPhotosLoadingState) -> Unit) {
        api.loadEarthPhotos(BuildConfig.NASA_API_KEY)
            .enqueue(object : Callback<List<EarthPhotoWrapper>> {
                override fun onResponse(
                    call: Call<List<EarthPhotoWrapper>>,
                    response: Response<List<EarthPhotoWrapper>>
                ) {
                    val body = response.body()
                    if (body == null) {
                        callback(EarthPhotosLoadingState.Error(Throwable("Server problem")))
                    } else {
                        callback(EarthPhotosLoadingState.Success(body))
                    }
                }

                override fun onFailure(call: Call<List<EarthPhotoWrapper>>, throwable: Throwable) {
                    callback(EarthPhotosLoadingState.Error(throwable))
                }
            })
    }

}