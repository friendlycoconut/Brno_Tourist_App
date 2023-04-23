package com.pv239.brnotouristapp.api


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File


object RetrofitUtil {

    private const val BASE_URL = "https://services6.arcgis.com/fUWVlHWZNxUvTUh8/arcgis/rest/services/PLACES/FeatureServer/0/"

    fun createGeoResource(): GeoPointWebService =
        create(BASE_URL, createOkHttpClient())

    private inline fun <reified T> create(baseUrl: String, okHttpClient: OkHttpClient): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(T::class.java)

    private fun createOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            addInterceptor(logging)
        }.build()
}