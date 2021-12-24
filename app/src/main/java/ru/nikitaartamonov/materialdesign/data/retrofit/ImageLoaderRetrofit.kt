package ru.nikitaartamonov.materialdesign.data.retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nikitaartamonov.materialdesign.BuildConfig
import ru.nikitaartamonov.materialdesign.domain.ImageLoader

private const val BASE_URL = "https://api.nasa.gov/"

class ImageLoaderRetrofit : ImageLoader {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: ImageApi = retrofit.create(ImageApi::class.java)

    override fun loadImage(callback: (ImageWrapper?) -> Unit) {
        api.getImage(BuildConfig.NASA_API_KEY).enqueue(object : Callback<ImageWrapper> {
            override fun onResponse(call: Call<ImageWrapper>, response: Response<ImageWrapper>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else callback(null)
            }

            override fun onFailure(call: Call<ImageWrapper>, t: Throwable) {
                callback(null)
            }
        })
    }

}