package com.example.pt_news_app.domain.repository

import android.R.attr.category
import android.R.attr.country
import com.example.pt_news_app.data.remote.api.ApiService
import com.example.pt_news_app.data.remote.dto.Article
import com.example.pt_news_app.domain.model.User

interface NewsRepository {
    suspend fun getNews(query: String, from: String, sortBy: String): Result<List<Article>>
}