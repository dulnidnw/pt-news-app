package com.example.pt_news_app.data.remote.api

import com.example.pt_news_app.data.remote.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/everything")
    suspend fun getTopHeadlines(
        @Query("country") country: Int,
        @Query("category") category: Int
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String
    ): NewsResponse
}