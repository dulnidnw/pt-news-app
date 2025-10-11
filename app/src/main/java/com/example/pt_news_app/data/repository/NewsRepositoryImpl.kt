package com.example.pt_news_app.data.repository

import android.R.attr.apiKey
import com.example.pt_news_app.data.remote.api.ApiService
import com.example.pt_news_app.data.remote.dto.Article
import com.example.pt_news_app.domain.repository.NewsRepository

class NewsRepositoryImpl(private val api: ApiService) : NewsRepository {

    override suspend fun getTopHeadlines(country: String, category: String): Result<List<Article>> {
        return try {
            val response = api.getTopHeadlines(country, category)
            if (response.status == "ok") Result.success(response.articles)
            else Result.failure(Exception("Error:${response.status}"))
            Result.success(response.articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}