package com.example.pt_news_app.data.remote.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object ApiClient {
    private var apiService: ApiService? = null
    private const val API_KEY = "a6a1ce5e0a4b4baf9b4fb649b90d241d"
    private const val BASE_URL = "https://newsapi.org/"

    fun getInstance(): ApiService {
        if (apiService == null) {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $API_KEY")
                        .build()
                    chain.proceed(request)
                }
                .build()

            apiService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
        return apiService!!
    }
}