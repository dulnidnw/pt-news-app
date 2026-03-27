package com.example.pt_news_app.data.remote.api

import com.example.pt_news_app.data.remote.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("from") fromDate: String,
        @Query("sortBy") sortBy: String = "popularity"
    ): NewsResponse
}