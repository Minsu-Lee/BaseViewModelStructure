package com.jackson.basestructure.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API {

    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val httpClient = if (BuildConfig.DEBUG) {
        OkHttpClient().newBuilder().addInterceptor(logging).build()
    } else {
        OkHttpClient().newBuilder().build()
    }


    private fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(UrlInfo.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        //.addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(httpClient)
        .build()

    fun <T> retrofit(service: Class<T>): T = retrofit().create(service)

}