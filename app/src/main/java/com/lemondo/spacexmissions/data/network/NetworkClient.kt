package com.lemondo.spacexmissions.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Manuchar Zakariadze on 2/28/22
 */
object NetworkClient {


    val spacexService by lazy { createHeroService() }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun createHeroService(): SpacexService {
        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.baseUrl("https://api.spacexdata.com/v3/")
        retrofitBuilder.client(
            OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor).build()
        )
        retrofitBuilder.addConverterFactory(moschiConvertor())
        return retrofitBuilder.build().create(SpacexService::class.java)
    }


    private fun moschiConvertor() =
        MoshiConverterFactory.create(
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        )

}