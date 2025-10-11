package com.example.pt_news_app.data.remote.api

import com.example.pt_news_app.data.remote.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String
    ): NewsResponse

    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String = "publishedAt"
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String
    ): NewsResponse
}