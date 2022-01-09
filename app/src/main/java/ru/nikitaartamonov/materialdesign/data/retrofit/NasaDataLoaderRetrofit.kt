package ru.nikitaartamonov.materialdesign.data.retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nikitaartamonov.materialdesign.BuildConfig
import ru.nikitaartamonov.materialdesign.domain.EarthPhotosLoadingState
import ru.nikitaartamonov.materialdesign.domain.GstDataLoadingState
import ru.nikitaartamonov.materialdesign.domain.ImageLoadingState
import ru.nikitaartamonov.materialdesign.domain.NasaDataLoader
import java.time.LocalDateTime

private const val BASE_URL = "https://api.nasa.gov/"

class NasaDataLoaderRetrofit : NasaDataLoader {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val api: NasaApi by lazy { retrofit.create(NasaApi::class.java) }

    override fun loadImage(callback: (ImageLoadingState) -> Unit) {
        val enqueueCallback = object : Callback<ImageWrapper> {
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
        }
        api.getDailyImage(BuildConfig.NASA_API_KEY).enqueue(enqueueCallback)
    }

    override fun loadEarthPhotos(callback: (EarthPhotosLoadingState) -> Unit) {
        val enqueueCallback = object : Callback<List<EarthPhotoWrapper>> {
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
        }
        api.loadEarthPhotos(BuildConfig.NASA_API_KEY).enqueue(enqueueCallback)
    }

    override fun loadGstData(callback: (GstDataLoadingState) -> Unit) {
        val endDate = LocalDateTime.now().toString().substring(0, 10)
        val endYear = endDate.substring(0, 4).toInt()
        val startYear = endYear - 1
        val startDate = "$startYear${endDate.substring(4)}"

        val enqueueCallback = object : Callback<List<GstWrapper>> {
            override fun onResponse(
                call: Call<List<GstWrapper>>,
                response: Response<List<GstWrapper>>
            ) {
                val body = response.body()
                if (body == null) {
                    callback(GstDataLoadingState.Error(Throwable("Server problem")))
                } else {
                    callback(GstDataLoadingState.Success(body))
                }
            }

            override fun onFailure(call: Call<List<GstWrapper>>, throwable: Throwable) {
                callback(GstDataLoadingState.Error(throwable))
            }
        }
        api.loadGstInfo(startDate, endDate, BuildConfig.NASA_API_KEY).enqueue(enqueueCallback)
    }

}