package com.example.pt_news_app.domain.repository

import android.R.attr.category
import android.R.attr.country
import com.example.pt_news_app.data.remote.api.ApiService
import com.example.pt_news_app.data.remote.dto.Article

class NewsRepository(private val api: ApiService) {

    suspend fun getTopNewsHeadlines(): Result<List<Article>> {
        return try {
            val response = api.getTopHeadlines(country, category)
//            val response = api.getNews(query, fromDate, fromDate)
            Result.success(response.articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchNews(query: String): Result<List<Article>> {
        return try {
            val response = api.searchNews(query)
            Result.success(response.articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}