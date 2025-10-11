package com.example.pt_news_app.domain.usecase

import androidx.room.Query
import com.example.pt_news_app.domain.repository.NewsRepository

class GetNewsUseCase(private val repo: NewsRepository) {
    suspend fun invoke(country: String, category: String) =
        repo.getTopHeadlines(country,category )
}