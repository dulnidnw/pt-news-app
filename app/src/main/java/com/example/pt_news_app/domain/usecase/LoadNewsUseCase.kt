package com.example.pt_news_app.domain.usecase

import com.example.pt_news_app.data.remote.dto.Article
import com.example.pt_news_app.domain.model.User
import com.example.pt_news_app.domain.repository.NewsRepository

class LoadNewsUseCase(private val repo: NewsRepository) {
    suspend operator fun invoke(country: String, category: String) =
        repo.getTopHeadlines(country, category)

    suspend fun getNewsByCategory(q: String) = repo.getNewsFeed(q)
//    suspend operator fun invoke(): List<User> = repo.getAllUsers()
}