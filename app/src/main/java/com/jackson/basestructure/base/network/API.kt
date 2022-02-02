package com.jackson.basestructure.base.network

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


    private fun retrofit(baseUrl: String = UrlInfo.BASE_URL, builderBlock: Retrofit.Builder.()->Unit = {}): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        //.addCallAdapterFactory(CoroutineCallAdapterFactory())
        .apply(builderBlock)
        .client(httpClient)
        .build()

    fun <T> typicode(service: Class<T>): T = retrofit(UrlInfo.BASE_URL).create(service)

//    fun <T> typicode(service: Class<T>): T = retrofit(UrlInfo.BASE_URL) {
//        // apply block
//        // addCallAdapterFactory(CoroutineCallAdapterFactory())
//    }.create(service)

}